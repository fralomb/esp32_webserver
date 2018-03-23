package it.polito.lombardo.esp_webserver.util;

import it.polito.lombardo.esp_webserver.entities.CollectedGPS;
import it.polito.lombardo.esp_webserver.entities.GPSDetail;
import it.polito.lombardo.esp_webserver.services.GPSElementsService;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import sun.misc.CRC16;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;
import java.util.Date;

@Component
@Scope("prototype")
public class TCPServerThread extends Thread{
    private static final int PORT = 40000;
    private static final int TIMEOUT = 10000;

    @Autowired
    private GPSElementsService gpsElementsService;

    @Override
    public void run(){
        //tcp server task...
        Socket server = null;
        DataInputStream in = null;
        DataInputStream in1 = null;

        while(true) {


            try {
                //try to connect
                ServerSocket serverSocket = new ServerSocket(PORT);
                System.out.println("Waiting for client on port " + serverSocket.getLocalPort() + "...");
                server = serverSocket.accept();
                System.out.println("Connected to [" + server.getRemoteSocketAddress() + "]");
                in = new DataInputStream(new BufferedInputStream(server.getInputStream()));


                //read imei length
                short imeiLength = in.readShort();
                if (imeiLength != 15) throw new RuntimeException("wrong Imei Length");
                //read imei
                byte[] imeiData = new byte[imeiLength];
                in.readFully(imeiData);
                String imei = new String(imeiData);
                System.out.println(" >>> " + imei);

                accept(server);

                //expect 4 zeros
                int zero = in.readInt();
                System.out.println("zero: "+zero);
                if (zero != 0) throw new RuntimeException("Expected four zero bytes");

                //data length
                int length = in.readInt();
                byte[] buffer = new byte[length];
                in.readFully(buffer);

                //AVL data received
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < length; i++)
                    sb.append(String.format("%02x ", buffer[i]));
                System.out.println("received: " + sb.toString());

                CRC16 crc16 = new CRC16();
                for(int i=0; i<length; i++) {
                    crc16.update(buffer[i]);
                }
                System.out.println("CRC: " + crc16.value);

                in1 = new DataInputStream(new ByteArrayInputStream(buffer));

                //read codec
                byte codec = in1.readByte();
                if (codec != 8) throw new RuntimeException("Invialid codec (" + codec + ")");

                //Number of Data
                int N = in1.readByte();
                System.out.println("there are "+ N + "GPS elements");

                CollectedGPS data = new CollectedGPS();
                data.imei = imei;
                data.timestamp = new Date();


                //GPS Elements
                for(int i=0; i<N; i++) {
                    GPSDetail detail = new GPSDetail();

                    long time = in1.readLong();
                    byte priority = in1.readByte();
                    int lng = in1.readInt();
                    int lat = in1.readInt();
                    short alt = in1.readShort();
                    short ang = in1.readShort();
                    byte sat = in1.readByte();
                    short speed = in1.readShort();

                    System.out.printf("time: %s, priority: %d, lng: %f, lat: %f, alt: %d, ang: %d, sat: %d, speed: %d\n", new Date(time).toString(), priority, lng / 1e7, lat / 1e7, alt, ang, sat, speed);

                    detail.timestamp = new Date(time);
                    detail.priority = priority;
                    detail.lng = lng / 1e7;
                    detail.lat = lat / 1e7;
                    detail.altitude = alt;
                    detail.angle = ang;
                    detail.satellites = sat;
                    detail.speed = speed;

                    data.getGpsData().add(detail);

                    byte eventId = in1.readByte();
                    byte n_io_total = in1.readByte();
                    byte n_io_1 = in1.readByte(); //quanti elementi di lunghezza 1
                    for (int j = 0; j < n_io_1; j++) {
                        byte element_id = in1.readByte();
                        byte element_value = in1.readByte();
                        System.out.printf("\t%x: %x\n", element_id, element_value);
                    }
                    byte n_io_2 = in1.readByte();
                    for (int j = 0; j < n_io_2; j++) {
                        byte element_id = in1.readByte();
                        short element_value = in1.readShort();
                        System.out.printf("\t%x: %x\n", element_id, element_value);
                    }
                    byte n_io_4 = in1.readByte();
                    for (int j = 0; j < n_io_4; j++) {
                        byte element_id = in1.readByte();
                        int element_value = in1.readInt();
                        System.out.printf("\t%x: %x\n", element_id, element_value);
                    }
                    byte n_io_8 = in1.readByte();
                    for (int j = 0; j < n_io_8; j++) {
                        byte element_id = in1.readByte();
                        long element_value = in1.readLong();
                        System.out.printf("\t%x: %lx\n", element_id, element_value);
                    }
                }
                //Number of data
                byte N1 = in1.readByte();
                if (N != N1) throw new RuntimeException("Wrong number of elements");
                System.out.println("N == N1 == "+ N1);

                //read crc16
                int crc = in.readInt();
                System.out.println("received crc: "+crc);

                if(crc16.value != crc)
                    System.out.println("wrong crc. Received ["+crc+"] vs. Computed ["+crc16.value+"] ");/*throw new RuntimeException("wrong crc");*/

                //save the stored information in a mongo collection
                gpsElementsService.saveCollectedData(data);

                //acknowledge data reception
                ack(server, N1);

            } catch (IOException e) {
                //e.printStackTrace();
                //System.out.println("IOException - "+e.getMessage());

            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Exception - "+e.getMessage());
                //continue to listening
                try {
                    reject(server);
                } catch (IOException e1) {
                    System.out.println("reject Exception - "+e1.getMessage());
                }
            }
        }
        //never free resources...
    }

    //accept the data
    private void accept(Socket server) throws IOException {
        server.getOutputStream().write(1);
        server.getOutputStream().flush();
    }

    //reject data
    private void reject(Socket server) throws IOException {
        server.getOutputStream().write(0);
        server.getOutputStream().flush();
    }

    //acknowledge server for the received data
    private void ack(Socket server, int numOfdata) throws IOException {
        server.getOutputStream().write(numOfdata);
        server.getOutputStream().flush();
    }
}
