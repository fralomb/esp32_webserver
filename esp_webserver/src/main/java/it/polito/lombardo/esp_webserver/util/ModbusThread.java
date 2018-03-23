package it.polito.lombardo.esp_webserver.util;

import com.digitalpetri.modbus.master.ModbusTcpMaster;
import com.digitalpetri.modbus.master.ModbusTcpMasterConfig;
import com.digitalpetri.modbus.requests.ReadHoldingRegistersRequest;
import com.digitalpetri.modbus.responses.ReadHoldingRegistersResponse;
import io.netty.buffer.ByteBufUtil;
import io.netty.util.ReferenceCountUtil;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
@Scope("prototype")
public class ModbusThread extends Thread {

    @Override
    public void run() {
        ModbusTcpMasterConfig config = new ModbusTcpMasterConfig.Builder("192.168.1.1").setPort(4500).build();
        ModbusTcpMaster master = new ModbusTcpMaster(config);
/*        System.out.println("Querying router for serial number... ");

        CompletableFuture<ReadHoldingRegistersResponse> future =
                master.sendRequest(new ReadHoldingRegistersRequest(39, 16), 0);

        future.thenAccept(response -> {
            System.out.println("SN: " + ByteBufUtil.hexDump(response.getRegisters()));

            ReferenceCountUtil.release(response);
        });


        System.out.println("Querying router for router mac address... ");
        CompletableFuture<ReadHoldingRegistersResponse> future2 =
                master.sendRequest(new ReadHoldingRegistersRequest(55, 16), 0);

        future2.thenAccept(response -> {
            System.out.println("MAC: " + ByteBufUtil.hexDump(response.getRegisters()));

            ReferenceCountUtil.release(response);
        });

        System.out.println("Querying router for router name... ");
        CompletableFuture<ReadHoldingRegistersResponse> future3 =
                master.sendRequest(new ReadHoldingRegistersRequest(71, 16), 0);

        future3.thenAccept(response -> {
            System.out.println("Name: " + ByteBufUtil.hexDump(response.getRegisters()));

            ReferenceCountUtil.release(response);
        });*/

        System.out.println("Querying router for router current sim card... ");
        CompletableFuture<ReadHoldingRegistersResponse> future4 =
                master.sendRequest(new ReadHoldingRegistersRequest(55, 2), 0);

        future4.thenAccept(response -> {
            byte[] bytes = new byte[response.getRegisters().readableBytes()];
            int index = response.getRegisters().readerIndex();
            response.getRegisters().getBytes(index, bytes);
            String sim = new String(bytes);
            System.out.println("SIM: " + sim);

            ReferenceCountUtil.release(response);
        });
    }
}
