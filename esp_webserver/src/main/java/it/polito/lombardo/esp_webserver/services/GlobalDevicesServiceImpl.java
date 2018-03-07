package it.polito.lombardo.esp_webserver.services;

import it.polito.lombardo.esp_webserver.documents.*;
import it.polito.lombardo.esp_webserver.entities.CollectedData;
import it.polito.lombardo.esp_webserver.entities.Detail;
import it.polito.lombardo.esp_webserver.entities.KnownDevice;
import it.polito.lombardo.esp_webserver.repositories.GlobalDataRepository;
import it.polito.lombardo.esp_webserver.repositories.GlobalDevicesRepository;
import it.polito.lombardo.esp_webserver.util.ActualTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class GlobalDevicesServiceImpl implements GlobalDevicesService {
    @Autowired
    private GlobalDataRepository globalDataRepo;

    @Autowired
    private GlobalDevicesRepository globalDevicesRepo;

    @Override
    public CollectedData saveCollectedData(CollectedData collectedData) {
        try {
            CollectedDataEntity data = globalDataRepo.findByChipIdAndUploadTimeout(collectedData.CHIP_ID, getDateFromString(collectedData.upload_timestamp));
            if (data == null) {
                //do not exist
                data = new CollectedDataEntity();
                data.setChipId(collectedData.CHIP_ID);
                data.setUploadTimeout(collectedData.upload_timeout);
                data.setUploadTimestamp(getDateFromString(collectedData.upload_timestamp));
                data.setUploadTimestampInMillis(getLongFromString(collectedData.upload_timestamp));
                //build the list of CollectedDeviceEntity
                List<CollectedDeviceEntity> devices = new ArrayList<CollectedDeviceEntity>();
                for (KnownDevice kd : collectedData.collected_data) {
                    //build the collectedDeviceEntity
                    CollectedDeviceEntity device = new CollectedDeviceEntity();
                    device.setGlobal_mac_address(kd.global_mac_address);
                    device.setEnd_time(getDateFromString(kd.end_time));
                    device.setEnd_time_millis(getLongFromString(kd.end_time));
                    device.setStart_time(getDateFromString(kd.start_time));
                    device.setStart_time_millis(getLongFromString(kd.start_time));
                    device.setFootprints(kd.footprints);
                    device.setUnique(kd.unique);
                    //build the list of ProbeEntity
                    List<ProbeEntity> probes = new ArrayList<ProbeEntity>();
                    for (Detail detail : kd.details) {
                        //build a  ProbeEntity
                        ProbeEntity p = new ProbeEntity();
                        if(!detail.ssid.isEmpty()) {
                            //add only if the ssid is empty
                            p.setSsid(detail.ssid);
                            p.setSsid_hash(detail.ssid_hash);
                        } else {
                            p.setSsid(null);
                            p.setSsid_hash(null);
                        }
                        //fill the two list
                        for (String d : detail.timestamps) {
                            p.addTimestamp(getDateFromString(d));
                            p.addTimestamp_millis(getLongFromString(d));
                        }
                        probes.add(p);
                    }
                    device.setProbeEntities(probes);
                    devices.add(device);
                }
                data.setCollectedDevices(devices);

                globalDataRepo.save(data);
                System.out.println("Data successfully added with _id:" + data.get_id() + ". Update timestamp and return back to esp32");
                //give the updated timestamp to the esp32
                collectedData.upload_timestamp = "" + ActualTime.getActualTimestampInMillis();

                //analyze collected data
                analyzeCollectedData(data);
            } else {
                System.out.println("the collection of data already exist in the db.");
            }
            return collectedData;
        } catch (NumberFormatException e) {
            System.out.println("ERROR - NumberFormatException raised.");
            return null;
        }
    }

    /*
   * return a Date from a long string
   * */
    private Date getDateFromString(String str) {
        Date d = new Date(getLongFromString(str));
        return d;
    }
    /*
    * return Long from a string
    * */
    private Long getLongFromString(String str) throws NumberFormatException{
        Long l = Long.parseLong(str);
        return l;
    }

    /*
    * Analyze these collected data to find reasonable identical devices
    *
    * */
    private void analyzeCollectedData(CollectedDataEntity collectedDataEntity) {
        for(CollectedDeviceEntity first : collectedDataEntity.getCollectedDevices()) {
            if(!first.getUnique()) {
                //the mac address is randomized
                boolean matched = false;

                //build the ssid set for "first"
                Set<Long> first_ssids_hash = new HashSet<Long>();
                for(ProbeEntity pe : first.getProbeEntities()) {
                    Long l = pe.getSsid_hash();
                    if(l != null)
                        first_ssids_hash.add(l);
                }
                //look for first
                GloballyUniqueDevicesEntity device = null;
                for(String Ffootprint : first.getFootprints()) {
                    device = globalDevicesRepo.findByChipIdAndFootprint(collectedDataEntity.getChipId(), Ffootprint);
                    if(device != null)
                        break;
                }

                for (CollectedDeviceEntity second : collectedDataEntity.getCollectedDevices()) {
                    if (!first.getGlobal_mac_address().equals(second.getGlobal_mac_address())) {
                        //the two devices are different
                        if (first.getStart_time_millis() > second.getEnd_time_millis() ||
                                first.getEnd_time_millis() < second.getStart_time_millis()) {
                            // |-------first------|
                            //                      |-------second------|
                            //   or...
                            // |-------second------|
                            //                      |-------first------|
                            for(String Sfootprint : second.getFootprints()) {
                                if (first.getFootprints().contains(Sfootprint)) {
                                    //the two devices have the same chipset
                                    //maybe are the same?
                                    //build the ssid set for "second"
                                    Set<Long> second_ssids_hash = new HashSet<Long>();
                                    for (ProbeEntity pe : second.getProbeEntities()) {
                                        Long l = pe.getSsid_hash();
                                        if (l != null)
                                            second_ssids_hash.add(l);
                                    }
                                    //NOTE: may add a penality if the unique ssid in common is ""
                                    Double similarity = intersection(first_ssids_hash, second_ssids_hash);
                                    if (similarity > 50) {
                                        matched = true;
                                        //the index of similarity is greater then the 50%
                                        //we can consider the two as the same device

                                        if (device != null) {
                                            //the device already exist, add some informations
                                            //insert first
                                            device = updateExistingDevice(device, first, collectedDataEntity.getChipId());
                                            //insert second
                                            device = updateExistingDevice(device, second, collectedDataEntity.getChipId());
                                        } else {
                                            //add a new device
                                            device = addNewDevice(first, first_ssids_hash, second, second_ssids_hash, collectedDataEntity.getChipId());
                                        }
                                        //update the collection
                                        globalDevicesRepo.save(device);
                                    }
                                    break;  //al least one footprint in common
                                }
                            }
                        }
                    }
                }
                if(!matched) {
                    //insert first as global device
                    //add a new device
                    if(device != null) {
                        //update the existing one
                        device = updateExistingDevice(device, first, collectedDataEntity.getChipId());
                    } else {
                        //add a new device
                        device = addNewDevice(first, first_ssids_hash, null, null, collectedDataEntity.getChipId());
                    }
                    //update the collection
                    globalDevicesRepo.save(device);
                }
            } else {
                //the mac address is unique, is a single device
                GloballyUniqueDevicesEntity device = null;
                for(String Ffootprint : first.getFootprints()) {
                    device = globalDevicesRepo.findByChipIdAndFootprint(collectedDataEntity.getChipId(), Ffootprint);
                    if(device != null)
                        break;
                }
                if(device == null) {
                    //not existing, add it.
                    Set<Long> first_ssids_hash = new HashSet<Long>();
                    for(ProbeEntity pe : first.getProbeEntities()) {
                        Long l = pe.getSsid_hash();
                        if(l != null)
                            first_ssids_hash.add(l);
                    }
                    device = addNewDevice(first, first_ssids_hash, null, null, collectedDataEntity.getChipId());
                } else {
                    //update informations about ssid notified
                    device = updateExistingDevice(device, first, collectedDataEntity.getChipId());
                }
                //update the collection
                globalDevicesRepo.save(device);
            }
        }
    }

    /*
    * intersection between two sets
    * return the percentage of elements in common
    * */
    public <T> Double intersection (Set<T> A, Set<T> B) {
        double A_old_size = (double) A.size();
        double B_size = (double) B.size();
        System.out.println("A: "+A);
        System.out.println("B: "+B);
        A.retainAll(B);
        double A_new_size = (double) A.size();
        System.out.println("intersection: "+A);
        //compute the similarity of the two set in percentage   | Jaccard index == |A int. B| / |A| + |B| - |A int. B|
        Double similarity = A_new_size / (A_old_size + B_size - A_new_size);
        System.out.println("similarity: "+similarity);
        return similarity * 100;
    }

    /*
    * save into GloballyUniqueDevicesEntity the CollectedDeviceEntity informations
    * */
    private GloballyUniqueDevicesEntity updateExistingDevice(GloballyUniqueDevicesEntity device, CollectedDeviceEntity data, String chipid) {
        device.getIntervals().add(new IntervalEntity(data.getStart_time(),
                data.getStart_time_millis(),
                data.getEnd_time(),
                data.getEnd_time_millis()));
        device.getFootprints().addAll(data.getFootprints());
        if(!device.getGlobal_mac_addresses().contains(data.getGlobal_mac_address())) {
            device.getGlobal_mac_addresses().add(data.getGlobal_mac_address());

            Set<String> first_ssids = new HashSet<String>();
            for (ProbeEntity pe : data.getProbeEntities()) {
                String s = pe.getSsid();
                if(s != null)
                    first_ssids.add(s);
            }
            device.getSsids().addAll(first_ssids);
        }
        return device;
    }

    private GloballyUniqueDevicesEntity addNewDevice(CollectedDeviceEntity first, Set<Long> first_ssids_hash, CollectedDeviceEntity second, Set<Long> second_ssids_hash, String chipid) {
        GloballyUniqueDevicesEntity device = new GloballyUniqueDevicesEntity();
        if(first != null && first_ssids_hash!=null) {
            device.getFootprints().addAll(first.getFootprints());
            device.setUnique(first.getUnique());
            /*device.setSimilarity_index(null);*/
            device.getChipIds().add(chipid);
            device.setFootprints(first.getFootprints());
            device.getGlobal_mac_addresses().add(first.getGlobal_mac_address());
            device.setIntervals(Arrays.asList(
                    new IntervalEntity(first.getStart_time(), first.getStart_time_millis(), first.getEnd_time(), first.getEnd_time_millis())
            ));
            Set<String> first_ssids = new HashSet<String>();
            for (ProbeEntity pe : first.getProbeEntities()) {
                String s = pe.getSsid();
                if(s != null)
                    first_ssids.add(s);
            }
            device.getSsids().addAll(first_ssids);
            device.getSsids_hash().addAll(first_ssids_hash);
        }

        if(second != null && second_ssids_hash !=null) {
            device.getFootprints().addAll(second.getFootprints());
            device.getGlobal_mac_addresses().add(second.getGlobal_mac_address());
            device.setIntervals(Arrays.asList(
                    new IntervalEntity(second.getStart_time(), second.getStart_time_millis(), second.getEnd_time(), second.getEnd_time_millis())
            ));
            Set<String> second_ssids = new HashSet<String>();
            for (ProbeEntity pe : second.getProbeEntities()) {
                String s = pe.getSsid();
                if(s != null)
                    second_ssids.add(s);
            }
            device.getSsids().addAll(second_ssids);
            device.getSsids_hash().addAll(second_ssids_hash);
        }
        return device;
    }

    /*
    * Analyze all the unresolved collected data sended by the the same esp32
    * */
    @Override
    public void analyzeCollectedDataBy(String chip_id) {


    }
}
