package com.luyao.vehicle.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.luyao.vehicle.common.Result;
import com.luyao.vehicle.entity.*;
import com.luyao.vehicle.entity.configDetail.*;
import com.luyao.vehicle.service.*;
import com.luyao.vehicle.utils.Hex;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;

@Service
public class TcpServiceImpl implements TcpService {

    String str = "";

    @Autowired
    private DataService dataService;

    @Autowired
    private BasicDataService basicDataService;

    @Autowired
    private BSMU1DataService bsmu1DataService;

    @Autowired
    private BSMU2DataService bsmu2DataService;

    @Autowired
    private VDPMConfigService vdpmConfigService;

    @Autowired
    private BSMU1BasicConfigService bsmu1BasicConfigService;

    @Autowired
    private BSMU1BearingConfigService bsmu1BearingConfigService;

    @Autowired
    private BSMU2BasicConfigService bsmu2BasicConfigService;

    @Autowired
    private BSMU2BearingConfigService bsmu2BearingConfigService;


    private ServerSocket server;

    public synchronized static void send(ServerThread serverThread, Socket client) throws IOException {

        // 往车辆端发送
        OutputStream out = client.getOutputStream();

        short[] s = new short[2];
        s[0] = 4;
        s[1] = serverThread.order;
        byte[] b = Hex.shortToBytes(s);
        out.write(b);
        out.flush();

        System.out.println("发送"+serverThread.order+"指令");

    }

    //发送102文件名
    public synchronized static void sendFileName(ServerThread serverThread, Socket client, String str, Short len) throws IOException {
        // 往车辆端发送
        OutputStream out = client.getOutputStream();

        short[] s = new short[2];
        s[0] = len;
        s[1] = serverThread.order;
        byte[] b = Hex.shortToBytes(s);

        byte[] fileName = str.getBytes(StandardCharsets.UTF_8);
        byte[] toSend = Hex.mergeBytes(b,fileName);
        out.write(toSend);
        out.flush();
        System.out.println("len 102"+str);
    }

    //写入VDPM配置
    public synchronized static void sendVDPMConfig(ServerThread serverThread, Socket client) throws IOException {

        // 往车辆端发送
        OutputStream out = client.getOutputStream();

        short[] s = new short[2];
        s[0] = 172;
        s[1] = serverThread.order;
        byte[] b = Hex.shortToBytes(s);
        VDPMConfig vdpmConfig = serverThread.vdpmConfigService.getOne(new QueryWrapper<VDPMConfig>().orderByDesc("datetime").last("limit 1"));
        byte[] config = vdpmConfig.getData();
        byte[] toSend = Hex.mergeBytes(b,config);
        out.write(toSend);
        out.flush();
        System.out.println("发送"+serverThread.order+"指令");
    }

    //写入BSMU1基础配置
    public synchronized static void sendBSMU1BasicConfig(ServerThread serverThread, Socket client) throws IOException {

        // 往车辆端发送
        OutputStream out = client.getOutputStream();

        short[] s = new short[2];
        s[0] = 92;
        s[1] = serverThread.order;
        byte[] b = Hex.shortToBytes(s);
        BSMU1BasicConfig bsmu1BasicConfig = serverThread.bsmu1BasicConfigService.getOne(new QueryWrapper<BSMU1BasicConfig>().orderByDesc("datetime").last("limit 1"));
        byte[] config = bsmu1BasicConfig.getData();
        byte[] toSend = Hex.mergeBytes(b,config);
        out.write(toSend);
        out.flush();
        System.out.println("发送"+serverThread.order+"指令");
    }

    //写入BSMU1轴承配置
    public synchronized static void sendBSMU1BearingConfig(ServerThread serverThread, Socket client) throws IOException {

        // 往车辆端发送
        OutputStream out = client.getOutputStream();

        short[] s = new short[2];
        s[0] = 280;
        s[1] = serverThread.order;
        byte[] b = Hex.shortToBytes(s);
        BSMU1BearingConfig bsmu1BearingConfig = serverThread.bsmu1BearingConfigService.getOne(new QueryWrapper<BSMU1BearingConfig>().orderByDesc("datetime").last("limit 1"));
        byte[] config = bsmu1BearingConfig.getData();
        byte[] toSend = Hex.mergeBytes(b,config);
        out.write(toSend);
        out.flush();
        System.out.println("发送"+serverThread.order+"指令");
    }

    //写入BSMU2基础配置
    public synchronized static void sendBSMU2BasicConfig(ServerThread serverThread, Socket client) throws IOException {

        // 往车辆端发送
        OutputStream out = client.getOutputStream();

        short[] s = new short[2];
        s[0] = 92;
        s[1] = serverThread.order;
        byte[] b = Hex.shortToBytes(s);
        BSMU2BasicConfig bsmu2BasicConfig = serverThread.bsmu2BasicConfigService.getOne(new QueryWrapper<BSMU2BasicConfig>().orderByDesc("datetime").last("limit 1"));
        byte[] config = bsmu2BasicConfig.getData();
        byte[] toSend = Hex.mergeBytes(b,config);
        out.write(toSend);
        out.flush();
        System.out.println("发送"+serverThread.order+"指令");
    }

    //写入BSMU2轴承配置
    public synchronized static void sendBSMU2BearingConfig(ServerThread serverThread, Socket client) throws IOException {

        // 往车辆端发送
        OutputStream out = client.getOutputStream();

        short[] s = new short[2];
        s[0] = 280;
        s[1] = serverThread.order;
        byte[] b = Hex.shortToBytes(s);
        BSMU2BearingConfig bsmu2BearingConfig = serverThread.bsmu2BearingConfigService.getOne(new QueryWrapper<BSMU2BearingConfig>().orderByDesc("datetime").last("limit 1"));
        byte[] config = bsmu2BearingConfig.getData();
        byte[] toSend = Hex.mergeBytes(b,config);
        out.write(toSend);
        out.flush();
        System.out.println("发送"+serverThread.order+"指令");
    }

    //接收实时监测数据53，54，15，35
    public synchronized static void receive(ServerThread serverThread, Socket client) throws IOException {

        // 从车辆端获取
        InputStream in = client.getInputStream();
        System.out.println(in.available());

        byte[] b1 = new byte[164];
        byte[] b2 = new byte[41];
        byte[] b3 = new byte[411];
        byte[] b4 = new byte[411];

        int readCount1 = in.read(b1);
        int readCount2 = in.read(b2);
        int readCount3 = in.read(b3);
        int readCount4 = in.read(b4);
        System.out.println("vdpm:"+readCount1+"vdpmbasic:"+readCount2+"bsmu1:"+readCount3+"bsmu2:"+readCount4);

        short[] s1 = Hex.bytesToShort(b1);
        if (s1[1] == 53 && b1.length == 164) {
            System.out.println("53收到");
            serverThread.dataService.save(new Data(new Timestamp(System.currentTimeMillis()), b1));
        }

        short[] s2 = Hex.bytesToShort(b2);
        if (s2[1] == 54 && b2.length == 41) {
            System.out.println("54收到");
            serverThread.basicDataService.save(new BasicData(new Timestamp(System.currentTimeMillis()), b2));
        }

        short[] s3 = Hex.bytesToShort(b3);
        if (s3[1] == 15 && b3.length == 411) {
            System.out.println("15收到");
            serverThread.bsmu1DataService.save(new BSMU1Data(new Timestamp(System.currentTimeMillis()), b3));
        }

        short[] s4 = Hex.bytesToShort(b4);
        if (s4[1] == 35 && b4.length == 411) {
            System.out.println("35收到");
            serverThread.bsmu2DataService.save(new BSMU2Data(new Timestamp(System.currentTimeMillis()), b4));
        }

    }

    //接收所有配置数据
    public synchronized static void receiveConfig(ServerThread serverThread, Socket client) throws IOException {

        // 从车辆端获取
        InputStream in = client.getInputStream();
        System.out.println(in.available());

        byte[] b1_flag = new byte[4];
        byte[] b1 = new byte[168];
        byte[] b2_flag = new byte[4];
        byte[] b2 = new byte[88];
        byte[] b3_flag = new byte[4];
        byte[] b3 = new byte[276];
        byte[] b4_flag = new byte[4];
        byte[] b4 = new byte[88];
        byte[] b5_flag = new byte[4];
        byte[] b5 = new byte[276];

        int readCount1_flag = in.read(b1_flag);
        int readCount1 = in.read(b1);
        int readCount2_flag = in.read(b2_flag);
        int readCount2 = in.read(b2);
        int readCount3_flag = in.read(b3_flag);
        int readCount3 = in.read(b3);
        int readCount4_flag = in.read(b4_flag);
        int readCount4 = in.read(b4);
        int readCount5_flag = in.read(b5_flag);
        int readCount5 = in.read(b5);
        System.out.println("vdpm配置:"+readCount1+"bsmu1轴承配置:"+readCount2+"bsmu1基础配置:"+readCount3+"bsmu2轴承配置:"+readCount4+"bsmu2基础配置:"+readCount5);

        short[] s1 = Hex.bytesToShort(b1_flag);
        if (s1[1] == 51) {
            System.out.println("51收到");
            serverThread.vdpmConfigService.save(new VDPMConfig(new Timestamp(System.currentTimeMillis()), b1));
        }

        short[] s2 = Hex.bytesToShort(b2_flag);
        if (s2[1] == 11) {
            System.out.println("11收到");
            serverThread.bsmu1BasicConfigService.save(new BSMU1BasicConfig(new Timestamp(System.currentTimeMillis()), b2));
        }

        short[] s3 = Hex.bytesToShort(b3_flag);
        if (s3[1] == 13) {
            System.out.println("13收到");
            serverThread.bsmu1BearingConfigService.save(new BSMU1BearingConfig(new Timestamp(System.currentTimeMillis()), b3));
        }

        short[] s4 = Hex.bytesToShort(b4_flag);
        if (s4[1] == 31) {
            System.out.println("31收到");
            serverThread.bsmu2BasicConfigService.save(new BSMU2BasicConfig(new Timestamp(System.currentTimeMillis()), b4));
        }

        short[] s5 = Hex.bytesToShort(b5_flag);
        if (s5[1] == 33) {
            System.out.println("33收到");
            serverThread.bsmu2BearingConfigService.save(new BSMU2BearingConfig(new Timestamp(System.currentTimeMillis()), b5));
        }
    }

    public synchronized static void receiveConfigMessage(ServerThread serverThread, Socket client) throws IOException {

        // 从车辆端获取
        InputStream in = client.getInputStream();
        System.out.println(in.available());

        byte[] b1_flag = new byte[4];
        byte[] b2_flag = new byte[4];
        byte[] b3_flag = new byte[4];
        byte[] b4_flag = new byte[4];
        byte[] b5_flag = new byte[4];

        int readCount1_flag = in.read(b1_flag);
        int readCount2_flag = in.read(b2_flag);
        int readCount3_flag = in.read(b3_flag);
        int readCount4_flag = in.read(b4_flag);
        int readCount5_flag = in.read(b5_flag);
        System.out.println("vdpm:"+readCount1_flag+"bsmu1basic"+readCount2_flag+"bsmu1bearing"+readCount3_flag+"bsmu2basic"+readCount4_flag+"bsmu2bearing"+readCount5_flag);

        short[] s1 = Hex.bytesToShort(b1_flag);
        if (s1[1] == 52) {
            System.out.println("vdpm配置写入成功");
        }

        short[] s2 = Hex.bytesToShort(b2_flag);
        if (s2[1] == 12) {
            System.out.println("bsmu1基础配置写入成功");
        }

        short[] s3 = Hex.bytesToShort(b3_flag);
        if (s3[1] == 14) {
            System.out.println("bsmu1轴承配置写入成功");
        }

        short[] s4 = Hex.bytesToShort(b4_flag);
        if (s4[1] == 32) {
            System.out.println("bsmu2基础配置写入成功");
        }

        short[] s5 = Hex.bytesToShort(b5_flag);
        if (s5[1] == 34) {
            System.out.println("bsmu2轴承配置写入成功");
        }
    }

    @Override
    public Result file() {
        try {
            server = new ServerSocket(22221);
            Socket client2 = server.accept();

            send(new ServerThread(dataService, basicDataService, bsmu1DataService, bsmu2DataService, vdpmConfigService, bsmu1BasicConfigService, bsmu1BearingConfigService, bsmu2BasicConfigService, bsmu2BearingConfigService, (short) 99), client2);
            Thread.sleep(2000);

            while(true) {

                InputStream in = client2.getInputStream();
                System.out.println(in.available());

                byte[] flag = new byte[4];
                int flag1 = in.read(flag);


                short[] s1 = Hex.bytesToShort(flag);
                int readFile = s1[0];
                System.out.println(readFile);
                if (s1[1] == 101) {
                    System.out.println("板卡返回文件名");
                } else if(s1[1] == 107){
                    System.out.println("所有文件传完了");
                    break;
                } else {
                    send(new ServerThread(dataService, basicDataService, bsmu1DataService, bsmu2DataService, vdpmConfigService, bsmu1BasicConfigService, bsmu1BearingConfigService, bsmu2BasicConfigService, bsmu2BearingConfigService, (short) 111), client2);
                    in.close();
                    break;
                }

                byte[] read = new byte[readFile];
                int ss = in.read(read);
                String str = new String(read, StandardCharsets.UTF_8);
                System.out.println(str);
                String dir = str.substring(0, str.lastIndexOf("/"));
                String detail = str.substring(0, str.lastIndexOf("/") + 1);
                System.out.println(dir);
                File Fdir = new File(dir);
                if (Fdir.isDirectory()) {
                    System.out.println("文件夹已存在");
                } else {
                    Fdir.mkdirs();
                    System.out.println("创建文件夹");
                }

                File file = new File(str.trim());
                if (file.exists()) {
                    System.out.println("文件已存在，删除并新建");
                    file.delete();
                    file.createNewFile();
                    System.out.println("原来存在的又重新创建文件");
                } else {
                    file.createNewFile();
                    System.out.println("创建文件");
                }

                send(new ServerThread(dataService, basicDataService, bsmu1DataService, bsmu2DataService, vdpmConfigService, bsmu1BasicConfigService, bsmu1BearingConfigService, bsmu2BasicConfigService, bsmu2BearingConfigService, (short) 102), client2);
                Thread.sleep(1000);

                //循环写入数据
                while (true) {
                    InputStream in3 = client2.getInputStream();
                    int ava = in3.available();
                    System.out.println(ava);


                    if (ava == 1024) {
                        byte[] flag4 = new byte[4];
                        byte[] read1020 = new byte[1020];
                        int flag5 = in3.read(flag4);
                        in3.read(read1020);

                        System.out.println("写入数据");
                        OutputStream fos = new FileOutputStream(str.trim(),true);
                        fos.write(read1020);
                        fos.flush();
                        fos.close();

                        send(new ServerThread(dataService, basicDataService, bsmu1DataService, bsmu2DataService, vdpmConfigService, bsmu1BasicConfigService, bsmu1BearingConfigService, bsmu2BasicConfigService, bsmu2BearingConfigService, (short) 104), client2);
                        Thread.sleep(1000);

                    } else {
                        byte[] flag4 = new byte[4];
                        int flag5 = in3.read(flag4);
                        short[] s11 = Hex.bytesToShort(flag4);
                        int readn = s11[0];
                        byte[] nn = new byte[readn];
                        in.read(nn);

                        System.out.println("不满1020，最后一次写入，退出循环");
                        OutputStream fos = new FileOutputStream(str.trim(),true);
                        fos.write(nn);
                        fos.flush();
                        fos.close();
                        send(new ServerThread(dataService, basicDataService, bsmu1DataService, bsmu2DataService, vdpmConfigService, bsmu1BasicConfigService, bsmu1BearingConfigService, bsmu2BasicConfigService, bsmu2BearingConfigService, (short) 104), client2);
                        Thread.sleep(1000);


                        break;
                    }
                }

                //4 105
                InputStream in4 = client2.getInputStream();
                byte[] flag44 = new byte[4];
                in4.read(flag44);
                short[] s44 = Hex.bytesToShort(flag44);
                if (s44[1] == 105) {
                    System.out.println("4 105收到");
                    send(new ServerThread(dataService, basicDataService, bsmu1DataService, bsmu2DataService, vdpmConfigService, bsmu1BasicConfigService, bsmu1BearingConfigService, bsmu2BasicConfigService, bsmu2BearingConfigService, (short) 106), client2);
                }


            }


            client2.close();
//            server.close();

            return Result.succ("创建文件");
        } catch (IOException e) {
            e.printStackTrace();
            return Result.fail("断开");
        } catch (InterruptedException e) {
            e.printStackTrace();
            return Result.fail("断开");
        }
    }

    @Override
    public Result changeConfig() {
        try {
            server = new ServerSocket(22221);

            // 每次连接更新配置
            Socket client1 = server.accept();

            sendVDPMConfig(new ServerThread(dataService, basicDataService, bsmu1DataService, bsmu2DataService, vdpmConfigService, bsmu1BasicConfigService, bsmu1BearingConfigService, bsmu2BasicConfigService, bsmu2BearingConfigService, (short) 52), client1);
            sendBSMU1BasicConfig(new ServerThread(dataService, basicDataService, bsmu1DataService, bsmu2DataService, vdpmConfigService, bsmu1BasicConfigService, bsmu1BearingConfigService, bsmu2BasicConfigService, bsmu2BearingConfigService, (short) 12), client1);
            sendBSMU1BearingConfig(new ServerThread(dataService, basicDataService, bsmu1DataService, bsmu2DataService, vdpmConfigService, bsmu1BasicConfigService, bsmu1BearingConfigService, bsmu2BasicConfigService, bsmu2BearingConfigService, (short) 14), client1);
            sendBSMU2BasicConfig(new ServerThread(dataService, basicDataService, bsmu1DataService, bsmu2DataService, vdpmConfigService, bsmu1BasicConfigService, bsmu1BearingConfigService, bsmu2BasicConfigService, bsmu2BearingConfigService, (short) 32), client1);
            sendBSMU2BearingConfig(new ServerThread(dataService, basicDataService, bsmu1DataService, bsmu2DataService, vdpmConfigService, bsmu1BasicConfigService, bsmu1BearingConfigService, bsmu2BasicConfigService, bsmu2BearingConfigService, (short) 34), client1);
            Thread.sleep(5000);
            receiveConfigMessage(new ServerThread(dataService, basicDataService, bsmu1DataService, bsmu2DataService, vdpmConfigService, bsmu1BasicConfigService, bsmu1BearingConfigService, bsmu2BasicConfigService, bsmu2BearingConfigService, (short) 100), client1);

            send(new ServerThread(dataService, basicDataService, bsmu1DataService, bsmu2DataService, vdpmConfigService, bsmu1BasicConfigService, bsmu1BearingConfigService, bsmu2BasicConfigService, bsmu2BearingConfigService, (short) 51), client1);
            send(new ServerThread(dataService, basicDataService, bsmu1DataService, bsmu2DataService, vdpmConfigService, bsmu1BasicConfigService, bsmu1BearingConfigService, bsmu2BasicConfigService, bsmu2BearingConfigService, (short) 11), client1);
            send(new ServerThread(dataService, basicDataService, bsmu1DataService, bsmu2DataService, vdpmConfigService, bsmu1BasicConfigService, bsmu1BearingConfigService, bsmu2BasicConfigService, bsmu2BearingConfigService, (short) 13), client1);
            send(new ServerThread(dataService, basicDataService, bsmu1DataService, bsmu2DataService, vdpmConfigService, bsmu1BasicConfigService, bsmu1BearingConfigService, bsmu2BasicConfigService, bsmu2BearingConfigService, (short) 31), client1);
            send(new ServerThread(dataService, basicDataService, bsmu1DataService, bsmu2DataService, vdpmConfigService, bsmu1BasicConfigService, bsmu1BearingConfigService, bsmu2BasicConfigService, bsmu2BearingConfigService, (short) 33), client1);
            Thread.sleep(5000);
            receiveConfig(new ServerThread(dataService, basicDataService, bsmu1DataService, bsmu2DataService, vdpmConfigService, bsmu1BasicConfigService, bsmu1BearingConfigService, bsmu2BasicConfigService, bsmu2BearingConfigService, (short) 100), client1);
            client1.close();
            server.close();
            return Result.succ("配置修改完毕");

        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail("断开");
        }

    }
    @Override
    public Result open() {

        try {
            server = new ServerSocket(22221);
            // 每隔10s循环获取实时监测信息
            while (true) {
                Socket client = server.accept();
                send(new ServerThread(dataService, basicDataService, bsmu1DataService, bsmu2DataService, vdpmConfigService, bsmu1BasicConfigService, bsmu1BearingConfigService, bsmu2BasicConfigService, bsmu2BearingConfigService, (short) 53), client);
                send(new ServerThread(dataService, basicDataService, bsmu1DataService, bsmu2DataService, vdpmConfigService, bsmu1BasicConfigService, bsmu1BearingConfigService, bsmu2BasicConfigService, bsmu2BearingConfigService, (short) 54), client);
                send(new ServerThread(dataService, basicDataService, bsmu1DataService, bsmu2DataService, vdpmConfigService, bsmu1BasicConfigService, bsmu1BearingConfigService, bsmu2BasicConfigService, bsmu2BearingConfigService, (short) 15), client);
                send(new ServerThread(dataService, basicDataService, bsmu1DataService, bsmu2DataService, vdpmConfigService, bsmu1BasicConfigService, bsmu1BearingConfigService, bsmu2BasicConfigService, bsmu2BearingConfigService, (short) 35), client);
                Thread.sleep(5000);
                receive(new ServerThread(dataService, basicDataService, bsmu1DataService, bsmu2DataService, vdpmConfigService, bsmu1BasicConfigService, bsmu1BearingConfigService, bsmu2BasicConfigService, bsmu2BearingConfigService, (short) 35), client);
                client.close();
                Thread.sleep(5000);

                //文件传输，1小时1次
//                Socket client1 = server.accept();
//                send(new ServerThread(dataService, basicDataService, bsmu1DataService, bsmu2DataService, vdpmConfigService, bsmu1BasicConfigService, bsmu1BearingConfigService, bsmu2BasicConfigService, bsmu2BearingConfigService, (short) 99), client1);
//                while(true) {
//                    //101 getStr
//                    InputStream in = client1.getInputStream();
//                    System.out.println(in.available());
//
//                    byte[] flag = new byte[4];
//                    int flag1 = in.read(flag);
//
//                    short[] s1 = Hex.bytesToShort(flag);
//                    short len = s1[0];
//                    if (s1[1] == 101) {
//                        System.out.println("返回文件名");
//                    } else {
//                        System.out.println("返回文件名异常，退出");
//                        in.close();
//                        send(new ServerThread(dataService, basicDataService, bsmu1DataService, bsmu2DataService, vdpmConfigService, bsmu1BasicConfigService, bsmu1BearingConfigService, bsmu2BasicConfigService, bsmu2BearingConfigService, (short) 111), client1);
//                        break;
//                    }
//
//                    byte[] res = in.readAllBytes();
//                    String str = new String(res, StandardCharsets.UTF_8);
//                    in.close();
//
//                    //len+102
//                    sendFileName(new ServerThread(dataService, basicDataService, bsmu1DataService, bsmu2DataService, vdpmConfigService, bsmu1BasicConfigService, bsmu1BearingConfigService, bsmu2BasicConfigService, bsmu2BearingConfigService, (short) 102), client1, str, len);
//
//                    //4 102 or 4 111
//                    InputStream in2 = client1.getInputStream();
//                    System.out.println(in2.available());
//
//                    byte[] flag2 = new byte[4];
//                    int flag3 = in2.read(flag);
//
//                    short[] s2 = Hex.bytesToShort(flag2);
//                    if (s2[1] == 102) {
//                        System.out.println("去创建文件吧");
//                        File file = new File(str);
//                        if(file.exists()){
//                            System.out.println("文件已存在，退出");
//                            in2.close();
//                            send(new ServerThread(dataService, basicDataService, bsmu1DataService, bsmu2DataService, vdpmConfigService, bsmu1BasicConfigService, bsmu1BearingConfigService, bsmu2BasicConfigService, bsmu2BearingConfigService, (short) 111), client1);
//                            break;
//                        }
//                        if(file.createNewFile()) {
//                            System.out.println("文件"+str+"创建成功");
//                            send(new ServerThread(dataService, basicDataService, bsmu1DataService, bsmu2DataService, vdpmConfigService, bsmu1BasicConfigService, bsmu1BearingConfigService, bsmu2BasicConfigService, bsmu2BearingConfigService, (short) 102), client1);
//                        }
//                    } else if(s2[1] == 111) {
//                        System.out.println("返回文件异常，退出");
//                        in2.close();
//                        send(new ServerThread(dataService, basicDataService, bsmu1DataService, bsmu2DataService, vdpmConfigService, bsmu1BasicConfigService, bsmu1BearingConfigService, bsmu2BasicConfigService, bsmu2BearingConfigService, (short) 111), client1);
//                        break;
//                    }
//                    in2.close();
//
//                    //循环写入数据
//                    while(true) {
//                        InputStream in3 = client1.getInputStream();
//                        System.out.println(in3.available());
//
//                        if(in3.available() == 1024) {
//                            byte[] flag4 = new byte[4];
//                            int flag5 = in3.read(flag4);
//
//                            System.out.println("写入数据");
//                            OutputStream fos = new FileOutputStream(str);
//                            fos.write(in3.readAllBytes());
//                            fos.flush();
//                            fos.close();
//                            in3.close();
//
//                            send(new ServerThread(dataService, basicDataService, bsmu1DataService, bsmu2DataService, vdpmConfigService, bsmu1BasicConfigService, bsmu1BearingConfigService, bsmu2BasicConfigService, bsmu2BearingConfigService, (short) 104), client1);
//                        } else {
//                            byte[] flag4 = new byte[4];
//                            int flag5 = in3.read(flag4);
//
//                            System.out.println("不满1020，最后一次写入，退出循环");
//                            OutputStream fos = new FileOutputStream(str);
//                            fos.write(in3.readAllBytes());
//                            fos.flush();
//                            fos.close();
//                            in3.close();
//
//                            break;
//                        }
//                    }
//                }
//                client1.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail("断开");
        }
    }

    @Override
    // 注意！！这些change是指保存到数据库
    public Result changeVDPMConfig(VDPMConfigDetail vdpmConfigDetail) {
        VDPMConfigService vdpmConfigService = this.vdpmConfigService;
        byte[] circuit = vdpmConfigDetail.getCircuit().getBytes();
        byte[] carModel = vdpmConfigDetail.getCarModel().getBytes();
        byte[] trainNumber = vdpmConfigDetail.getTrainNumber().getBytes();
        byte[] crateNumber = vdpmConfigDetail.getCrateNumber().getBytes();
        byte[] softVersion = vdpmConfigDetail.getSoftVersion().getBytes();
        byte[] hardVersion = vdpmConfigDetail.getHardVersion().getBytes();
        byte[] configVersion = vdpmConfigDetail.getConfigVersion().getBytes();
        byte[] bu = "0000".getBytes();

        byte[] vehicleNumber = Hex.intToByteArray(Integer.parseInt(vdpmConfigDetail.getVehicleNumber()));
        byte[] wheelDiameter = Hex.intToByteArray(Integer.parseInt(vdpmConfigDetail.getWheelDiameter()));
        byte[] speedGearNumber = Hex.intToByteArray(Integer.parseInt(vdpmConfigDetail.getSpeedGearNumber()));
        byte[] mainNumber = Hex.intToByteArray(Integer.parseInt(vdpmConfigDetail.getMainNumber()));
        byte[] otherNumber = Hex.intToByteArray(Integer.parseInt(vdpmConfigDetail.getOtherNumber()));
        byte[] levelPressure = Hex.intToByteArray(Integer.parseInt(vdpmConfigDetail.getLevelPressure()));
        byte[] emptyWeight = Hex.float2ByteArray(Float.parseFloat(vdpmConfigDetail.getEmptyWeight()));
        byte[] totalDistance = Hex.double2ByteArray(Double.parseDouble(vdpmConfigDetail.getTotalDistance()));
        byte[] overWeight = Hex.float2ByteArray(Float.parseFloat(vdpmConfigDetail.getOverWeight()));

        byte[] windingEarlyWarning = Hex.intToByteArray(Integer.parseInt(vdpmConfigDetail.getWindingEarlyWarning()));
        byte[] windingWarning = Hex.intToByteArray(Integer.parseInt(vdpmConfigDetail.getWindingWarning()));
        byte[] batteryEarlyWarning = Hex.intToByteArray(Integer.parseInt(vdpmConfigDetail.getBatteryEarlyWarning()));
        byte[] batteryWarning = Hex.intToByteArray(Integer.parseInt(vdpmConfigDetail.getBatteryWarning()));

        byte[] dataSamplingRate = Hex.intToByteArray(Integer.parseInt(vdpmConfigDetail.getDataSamplingRate()));
        byte[] sensorSensitivity = Hex.float2ByteArray(Float.parseFloat(vdpmConfigDetail.getSensorSensitivity()));
        byte[] dataLength = Hex.intToByteArray(Integer.parseInt(vdpmConfigDetail.getDataLength()));
        byte[] balance1 = Hex.double2ByteArray(Double.parseDouble(vdpmConfigDetail.getBalance1()));
        byte[] balance2 = Hex.double2ByteArray(Double.parseDouble(vdpmConfigDetail.getBalance2()));
        byte[] balance3 = Hex.double2ByteArray(Double.parseDouble(vdpmConfigDetail.getBalance3()));
        byte[] lateralQuality = Hex.double2ByteArray(Double.parseDouble(vdpmConfigDetail.getLateralQuality()));
        byte[] verticalQuality = Hex.double2ByteArray(Double.parseDouble(vdpmConfigDetail.getVerticalQuality()));
        byte[] minAnalysisVelocity = Hex.intToByteArray(Integer.parseInt(vdpmConfigDetail.getMinAnalysisVelocity()));

        byte[] res = Hex.mergeBytes(circuit,
                bu,
                carModel,
                bu,
                trainNumber,
                bu,
                vehicleNumber,
                wheelDiameter,
                speedGearNumber,
                crateNumber,
                softVersion,
                hardVersion,
                configVersion,
                mainNumber,
                otherNumber,
                levelPressure,
                emptyWeight,
                totalDistance,
                overWeight,
                windingEarlyWarning,
                windingWarning,
                batteryEarlyWarning,
                batteryWarning,
                dataSamplingRate,
                sensorSensitivity,
                dataLength,
                balance1,
                balance2,
                balance3,
                lateralQuality,
                verticalQuality,
                minAnalysisVelocity
        );

        vdpmConfigService.save(new VDPMConfig(new Timestamp(System.currentTimeMillis()),res));

        return Result.succ(res.length);
    }

    @Override
    public Result changeBSMU1BasicConfig(BSMU1BasicConfigDetail bsmu1BasicConfigDetail) {
        BSMU1BasicConfigService bsmu1BasicConfigService = this.bsmu1BasicConfigService;
        byte[] boardNumber = Hex.intToByteArray(Integer.parseInt(bsmu1BasicConfigDetail.getBoardNumber()));
        byte[] wheelDiameter = Hex.intToByteArray(Integer.parseInt(bsmu1BasicConfigDetail.getWheelDiameter()));
        byte[] numGetSpeedGearTooth = Hex.intToByteArray(Integer.parseInt(bsmu1BasicConfigDetail.getNumGetSpeedGearTooth()));
        byte[] k_emptyVertical = Hex.float2ByteArray(Float.parseFloat(bsmu1BasicConfigDetail.getK_emptyVertical()));
        byte[] k_fullVertical = Hex.float2ByteArray(Float.parseFloat(bsmu1BasicConfigDetail.getK_fullVertical()));
        byte[] th_kDifference = Hex.float2ByteArray(Float.parseFloat(bsmu1BasicConfigDetail.getTh_kDifference()));

        byte[] hunting_fs = Hex.float2ByteArray(Float.parseFloat(bsmu1BasicConfigDetail.getHunting_fs()));
        byte[] hunting_f2 = Hex.float2ByteArray(Float.parseFloat(bsmu1BasicConfigDetail.getHunting_f2()));
        byte[] hunting_f3 = Hex.float2ByteArray(Float.parseFloat(bsmu1BasicConfigDetail.getHunting_f3()));
        byte[] hunting_f4 = Hex.float2ByteArray(Float.parseFloat(bsmu1BasicConfigDetail.getHunting_f4()));
        byte[] hunting_N1 = Hex.intToByteArray(Integer.parseInt(bsmu1BasicConfigDetail.getHunting_N1()));
        byte[] hunting_N2 = Hex.intToByteArray(Integer.parseInt(bsmu1BasicConfigDetail.getHunting_N2()));
        byte[] hunting_S = Hex.intToByteArray(Integer.parseInt(bsmu1BasicConfigDetail.getHunting_S()));
        byte[] hunting_TR0 = Hex.float2ByteArray(Float.parseFloat(bsmu1BasicConfigDetail.getHunting_TR0()));
        byte[] hunting_TR1 = Hex.float2ByteArray(Float.parseFloat(bsmu1BasicConfigDetail.getHunting_TR1()));
        byte[] hunting_TR2 = Hex.float2ByteArray(Float.parseFloat(bsmu1BasicConfigDetail.getHunting_TR2()));
        byte[] hunting_D1 = Hex.intToByteArray(Integer.parseInt(bsmu1BasicConfigDetail.getHunting_D1()));
        byte[] hunting_D = Hex.intToByteArray(Integer.parseInt(bsmu1BasicConfigDetail.getHunting_D()));
        byte[] hunting_H_mag = Hex.float2ByteArray(Float.parseFloat(bsmu1BasicConfigDetail.getHunting_H_mag()));
        byte[] hunting_sensitivity = Hex.float2ByteArray(Float.parseFloat(bsmu1BasicConfigDetail.getHunting_sensitivity()));
        byte[] hunting_nword = Hex.intToByteArray(Integer.parseInt(bsmu1BasicConfigDetail.getHunting_nword()));
        byte[] hunting_minspeed = Hex.intToByteArray(Integer.parseInt(bsmu1BasicConfigDetail.getHunting_minspeed()));

        byte[] res = Hex.mergeBytes(
                boardNumber,
                wheelDiameter,
                numGetSpeedGearTooth,
                k_emptyVertical,
                k_fullVertical,
                th_kDifference,
                hunting_fs,
                hunting_f2,
                hunting_f3,
                hunting_f4,
                hunting_N1,
                hunting_N2,
                hunting_S,
                hunting_TR0,
                hunting_TR1,
                hunting_TR2,
                hunting_D1,
                hunting_D,
                hunting_H_mag,
                hunting_sensitivity,
                hunting_nword,
                hunting_minspeed
        );

        bsmu1BasicConfigService.save(new BSMU1BasicConfig(new Timestamp(System.currentTimeMillis()),res));
        return Result.succ(res.length);

    }

    @Override
    public Result changeBSMU2BasicConfig(BSMU2BasicConfigDetail bsmu2BasicConfigDetail) {
        BSMU2BasicConfigService bsmu2BasicConfigService = this.bsmu2BasicConfigService;
        byte[] boardNumber = Hex.intToByteArray(Integer.parseInt(bsmu2BasicConfigDetail.getBoardNumber()));
        byte[] wheelDiameter = Hex.intToByteArray(Integer.parseInt(bsmu2BasicConfigDetail.getWheelDiameter()));
        byte[] numGetSpeedGearTooth = Hex.intToByteArray(Integer.parseInt(bsmu2BasicConfigDetail.getNumGetSpeedGearTooth()));
        byte[] k_emptyVertical = Hex.float2ByteArray(Float.parseFloat(bsmu2BasicConfigDetail.getK_emptyVertical()));
        byte[] k_fullVertical = Hex.float2ByteArray(Float.parseFloat(bsmu2BasicConfigDetail.getK_fullVertical()));
        byte[] th_kDifference = Hex.float2ByteArray(Float.parseFloat(bsmu2BasicConfigDetail.getTh_kDifference()));

        byte[] hunting_fs = Hex.float2ByteArray(Float.parseFloat(bsmu2BasicConfigDetail.getHunting_fs()));
        byte[] hunting_f2 = Hex.float2ByteArray(Float.parseFloat(bsmu2BasicConfigDetail.getHunting_f2()));
        byte[] hunting_f3 = Hex.float2ByteArray(Float.parseFloat(bsmu2BasicConfigDetail.getHunting_f3()));
        byte[] hunting_f4 = Hex.float2ByteArray(Float.parseFloat(bsmu2BasicConfigDetail.getHunting_f4()));
        byte[] hunting_N1 = Hex.intToByteArray(Integer.parseInt(bsmu2BasicConfigDetail.getHunting_N1()));
        byte[] hunting_N2 = Hex.intToByteArray(Integer.parseInt(bsmu2BasicConfigDetail.getHunting_N2()));
        byte[] hunting_S = Hex.intToByteArray(Integer.parseInt(bsmu2BasicConfigDetail.getHunting_S()));
        byte[] hunting_TR0 = Hex.float2ByteArray(Float.parseFloat(bsmu2BasicConfigDetail.getHunting_TR0()));
        byte[] hunting_TR1 = Hex.float2ByteArray(Float.parseFloat(bsmu2BasicConfigDetail.getHunting_TR1()));
        byte[] hunting_TR2 = Hex.float2ByteArray(Float.parseFloat(bsmu2BasicConfigDetail.getHunting_TR2()));
        byte[] hunting_D1 = Hex.intToByteArray(Integer.parseInt(bsmu2BasicConfigDetail.getHunting_D1()));
        byte[] hunting_D = Hex.intToByteArray(Integer.parseInt(bsmu2BasicConfigDetail.getHunting_D()));
        byte[] hunting_H_mag = Hex.float2ByteArray(Float.parseFloat(bsmu2BasicConfigDetail.getHunting_H_mag()));
        byte[] hunting_sensitivity = Hex.float2ByteArray(Float.parseFloat(bsmu2BasicConfigDetail.getHunting_sensitivity()));
        byte[] hunting_nword = Hex.intToByteArray(Integer.parseInt(bsmu2BasicConfigDetail.getHunting_nword()));
        byte[] hunting_minspeed = Hex.intToByteArray(Integer.parseInt(bsmu2BasicConfigDetail.getHunting_minspeed()));

        byte[] res = Hex.mergeBytes(
                boardNumber,
                wheelDiameter,
                numGetSpeedGearTooth,
                k_emptyVertical,
                k_fullVertical,
                th_kDifference,
                hunting_fs,
                hunting_f2,
                hunting_f3,
                hunting_f4,
                hunting_N1,
                hunting_N2,
                hunting_S,
                hunting_TR0,
                hunting_TR1,
                hunting_TR2,
                hunting_D1,
                hunting_D,
                hunting_H_mag,
                hunting_sensitivity,
                hunting_nword,
                hunting_minspeed
        );

        bsmu2BasicConfigService.save(new BSMU2BasicConfig(new Timestamp(System.currentTimeMillis()),res));
        return Result.succ(res.length);

    }

    @Override
    public Result changeBSMU1BearingConfig(BSMU1BearingConfigDetail bsmu1BearingConfigDetail) {
        BSMU1BearingConfigService bsmu1BearingConfigService = this.bsmu1BearingConfigService;
        byte[] bearingType = bsmu1BearingConfigDetail.getBeeringType().getBytes();

        byte[] bearingShaftDiameter = Hex.float2ByteArray(Float.parseFloat(bsmu1BearingConfigDetail.getBearingShaftDiameter()));
        byte[] bearingOutDiameter = Hex.float2ByteArray(Float.parseFloat(bsmu1BearingConfigDetail.getBearingOutDiameter()));
        byte[] bearingMidDiameter = Hex.float2ByteArray(Float.parseFloat(bsmu1BearingConfigDetail.getBearingMidDiameter()));
        byte[] rollerDiameter = Hex.float2ByteArray(Float.parseFloat(bsmu1BearingConfigDetail.getRollerDiameter()));
        byte[] numRoller = Hex.intToByteArray(Integer.parseInt(bsmu1BearingConfigDetail.getNumRoller()));
        byte[] rowRoller = Hex.intToByteArray(Integer.parseInt(bsmu1BearingConfigDetail.getRowRoller()));
        byte[] contactAngle = Hex.float2ByteArray(Float.parseFloat(bsmu1BearingConfigDetail.getContactAngle()));
        byte[] ratioNeighbor = Hex.float2ByteArray(Float.parseFloat(bsmu1BearingConfigDetail.getRatioNeighbor()));
        byte[] ratioWheel = Hex.float2ByteArray(Float.parseFloat(bsmu1BearingConfigDetail.getRatioWheel()));
        byte[] numLargeTeeth = Hex.intToByteArray(Integer.parseInt(bsmu1BearingConfigDetail.getNumLargeTeeth()));
        byte[] numSmallTeeth = Hex.intToByteArray(Integer.parseInt(bsmu1BearingConfigDetail.getNumSmallTeeth()));

        byte[] mev_outCage = Hex.float2ByteArray(Float.parseFloat(bsmu1BearingConfigDetail.getMev_outCage()));
        byte[] mev_inCage = Hex.float2ByteArray(Float.parseFloat(bsmu1BearingConfigDetail.getMev_inCage()));
        byte[] mev_outRing = Hex.float2ByteArray(Float.parseFloat(bsmu1BearingConfigDetail.getMev_outRing()));
        byte[] mev_inRing = Hex.float2ByteArray(Float.parseFloat(bsmu1BearingConfigDetail.getMev_inRing()));
        byte[] mev_singleRoller = Hex.float2ByteArray(Float.parseFloat(bsmu1BearingConfigDetail.getMev_singleRoller()));
        byte[] mev_doubleRoller = Hex.float2ByteArray(Float.parseFloat(bsmu1BearingConfigDetail.getMev_doubleRoller()));
        byte[] mev_gear_tread = Hex.float2ByteArray(Float.parseFloat(bsmu1BearingConfigDetail.getMev_gear_tread()));
        byte[] mev_nearTeeth = Hex.float2ByteArray(Float.parseFloat(bsmu1BearingConfigDetail.getMev_nearTeeth()));

        byte[] th_tempRiseWarning = Hex.intToByteArray(Integer.parseInt(bsmu1BearingConfigDetail.getTh_tempRiseWarning()));
        byte[] th_tempRiseAlarm = Hex.intToByteArray(Integer.parseInt(bsmu1BearingConfigDetail.getTh_tempRiseAlarm()));
        byte[] th_tempWarning = Hex.intToByteArray(Integer.parseInt(bsmu1BearingConfigDetail.getTh_tempWarning()));
        byte[] th_tempAlarm = Hex.intToByteArray(Integer.parseInt(bsmu1BearingConfigDetail.getTh_tempAlarm()));
        byte[] th_outCageAlarm1 = Hex.intToByteArray(Integer.parseInt(bsmu1BearingConfigDetail.getTh_outCageAlarm1()));
        byte[] th_outCageAlarm2 = Hex.intToByteArray(Integer.parseInt(bsmu1BearingConfigDetail.getTh_outCageAlarm2()));
        byte[] th_outCageAlarm3 = Hex.intToByteArray(Integer.parseInt(bsmu1BearingConfigDetail.getTh_outCageAlarm3()));
        byte[] th_inCageAlarm1 = Hex.intToByteArray(Integer.parseInt(bsmu1BearingConfigDetail.getTh_inCageAlarm1()));
        byte[] th_inCageAlarm2 = Hex.intToByteArray(Integer.parseInt(bsmu1BearingConfigDetail.getTh_inCageAlarm2()));
        byte[] th_inCageAlarm3 = Hex.intToByteArray(Integer.parseInt(bsmu1BearingConfigDetail.getTh_inCageAlarm3()));
        byte[] th_outRingAlarm1 = Hex.intToByteArray(Integer.parseInt(bsmu1BearingConfigDetail.getTh_outRingAlarm1()));
        byte[] th_outRingAlarm2 = Hex.intToByteArray(Integer.parseInt(bsmu1BearingConfigDetail.getTh_outRingAlarm2()));
        byte[] th_outRingAlarm3 = Hex.intToByteArray(Integer.parseInt(bsmu1BearingConfigDetail.getTh_outRingAlarm3()));
        byte[] th_inRingAlarm1 = Hex.intToByteArray(Integer.parseInt(bsmu1BearingConfigDetail.getTh_inRingAlarm1()));
        byte[] th_inRingAlarm2 = Hex.intToByteArray(Integer.parseInt(bsmu1BearingConfigDetail.getTh_inRingAlarm2()));
        byte[] th_inRingAlarm3 = Hex.intToByteArray(Integer.parseInt(bsmu1BearingConfigDetail.getTh_inRingAlarm3()));
        byte[] th_singleRollerAlarm1 = Hex.intToByteArray(Integer.parseInt(bsmu1BearingConfigDetail.getTh_singleRollerAlarm1()));
        byte[] th_singleRollerAlarm2 = Hex.intToByteArray(Integer.parseInt(bsmu1BearingConfigDetail.getTh_singleRollerAlarm2()));
        byte[] th_singleRollerAlarm3 = Hex.intToByteArray(Integer.parseInt(bsmu1BearingConfigDetail.getTh_singleRollerAlarm3()));
        byte[] th_doubleRollerAlarm1 = Hex.intToByteArray(Integer.parseInt(bsmu1BearingConfigDetail.getTh_doubleRollerAlarm1()));
        byte[] th_doubleRollerAlarm2 = Hex.intToByteArray(Integer.parseInt(bsmu1BearingConfigDetail.getTh_doubleRollerAlarm2()));
        byte[] th_doubleRollerAlarm3 = Hex.intToByteArray(Integer.parseInt(bsmu1BearingConfigDetail.getTh_doubleRollerAlarm3()));
        byte[] th_treadAlarm1 = Hex.intToByteArray(Integer.parseInt(bsmu1BearingConfigDetail.getTh_treadAlarm1()));
        byte[] th_treadAlarm2 = Hex.intToByteArray(Integer.parseInt(bsmu1BearingConfigDetail.getTh_treadAlarm2()));
        byte[] th_treadAlarm3 = Hex.intToByteArray(Integer.parseInt(bsmu1BearingConfigDetail.getTh_treadAlarm3()));
        byte[] th_nearGearAlarm1 = Hex.intToByteArray(Integer.parseInt(bsmu1BearingConfigDetail.getTh_nearGearAlarm1()));
        byte[] th_nearGearAlarm2 = Hex.intToByteArray(Integer.parseInt(bsmu1BearingConfigDetail.getTh_nearGearAlarm2()));
        byte[] th_nearGearAlarm3 = Hex.intToByteArray(Integer.parseInt(bsmu1BearingConfigDetail.getTh_nearGearAlarm3()));

        byte[] minSpeed_kmh = Hex.intToByteArray(Integer.parseInt(bsmu1BearingConfigDetail.getMinSpeed_kmh()));
        byte[] demudulateGainCoef = Hex.float2ByteArray(Float.parseFloat(bsmu1BearingConfigDetail.getDemudulateGainCoef()));

        byte[] speedMultiFreqCoef = Hex.fromUnsignedInt(Long.parseLong(bsmu1BearingConfigDetail.getSpeedMultiFreqCoef()));
        byte[] highPassFilterGain = Hex.fromUnsignedInt(Long.parseLong(bsmu1BearingConfigDetail.getHighPassFilterGain()));
        byte[] bandPassFilterGain = Hex.fromUnsignedInt(Long.parseLong(bsmu1BearingConfigDetail.getBandPassFilterGain()));
        byte[] detectFilterGain = Hex.fromUnsignedInt(Long.parseLong(bsmu1BearingConfigDetail.getDetectFilterGain()));
        byte[] lowPassFilterGain = Hex.fromUnsignedInt(Long.parseLong(bsmu1BearingConfigDetail.getLowPassFilterGain()));
        byte[] lowPassFilterGain_8 = Hex.fromUnsignedInt(Long.parseLong(bsmu1BearingConfigDetail.getLowPassFilterGain_8()));
        byte[] lowPassFilterGain_58 = Hex.fromUnsignedInt(Long.parseLong(bsmu1BearingConfigDetail.getLowPassFilterGain_58()));
        byte[] lowPassFilterFreq_58 = Hex.fromUnsignedInt(Long.parseLong(bsmu1BearingConfigDetail.getLowPassFilterFreq_58()));
        byte[] originalInterval = Hex.fromUnsignedInt(Long.parseLong(bsmu1BearingConfigDetail.getOriginalInterval()));
        byte[] packSizeEachTime = Hex.fromUnsignedInt(Long.parseLong(bsmu1BearingConfigDetail.getPackSizeEachTime()));
        byte[] packSizeAll = Hex.fromUnsignedInt(Long.parseLong(bsmu1BearingConfigDetail.getPackSizeAll()));
        byte[] gainCoef_58 = Hex.fromUnsignedInt(Long.parseLong(bsmu1BearingConfigDetail.getGainCoef_58()));
        byte[] numFFTPoint = Hex.fromUnsignedInt(Long.parseLong(bsmu1BearingConfigDetail.getNumFFTPoint()));
        byte[] isSimulateSpeed = Hex.fromUnsignedInt(Long.parseLong(bsmu1BearingConfigDetail.getIsSimulateSpeed()));
        byte[] originalSampleRate = Hex.fromUnsignedInt(Long.parseLong(bsmu1BearingConfigDetail.getOriginalSampleRate()));
        byte[] originalSampleDuration = Hex.fromUnsignedInt(Long.parseLong(bsmu1BearingConfigDetail.getOriginalSampleDuration()));
        byte[] originalSampleLabel = Hex.fromUnsignedInt(Long.parseLong(bsmu1BearingConfigDetail.getOriginalSampleLabel()));

        byte[] res = Hex.mergeBytes(
                bearingType,
                bearingShaftDiameter,
                bearingOutDiameter,
                bearingMidDiameter,
                rollerDiameter,
                numRoller,
                rowRoller,
                contactAngle,
                ratioNeighbor,
                ratioWheel,
                numLargeTeeth,
                numSmallTeeth,
                mev_outCage,
                mev_inCage,
                mev_outRing,
                mev_inRing,
                mev_singleRoller,
                mev_doubleRoller,
                mev_gear_tread,
                mev_nearTeeth,
                th_tempRiseWarning,
                th_tempRiseAlarm,
                th_tempWarning,
                th_tempAlarm,
                th_outCageAlarm1,
                th_outCageAlarm2,
                th_outCageAlarm3,
                th_inCageAlarm1,
                th_inCageAlarm2,
                th_inCageAlarm3,
                th_outRingAlarm1,
                th_outRingAlarm2,
                th_outRingAlarm3,
                th_inRingAlarm1,
                th_inRingAlarm2,
                th_inRingAlarm3,
                th_singleRollerAlarm1,
                th_singleRollerAlarm2,
                th_singleRollerAlarm3,
                th_doubleRollerAlarm1,
                th_doubleRollerAlarm2,
                th_doubleRollerAlarm3,
                th_treadAlarm1,
                th_treadAlarm2,
                th_treadAlarm3,
                th_nearGearAlarm1,
                th_nearGearAlarm2,
                th_nearGearAlarm3,
                minSpeed_kmh,
                demudulateGainCoef,
                speedMultiFreqCoef,
                highPassFilterGain,
                bandPassFilterGain,
                detectFilterGain,
                lowPassFilterGain,
                lowPassFilterGain_8,
                lowPassFilterGain_58,
                lowPassFilterFreq_58,
                originalInterval,
                packSizeEachTime,
                packSizeAll,
                gainCoef_58,
                numFFTPoint,
                isSimulateSpeed,
                originalSampleRate,
                originalSampleDuration,
                originalSampleLabel

        );

        bsmu1BearingConfigService.save(new BSMU1BearingConfig(new Timestamp(System.currentTimeMillis()),res));

        return Result.succ(res.length);
    }

    @Override
    public Result changeBSMU2BearingConfig(BSMU2BearingConfigDetail bsmu2BearingConfigDetail) {
        BSMU2BearingConfigService bsmu2BearingConfigService = this.bsmu2BearingConfigService;
        byte[] bearingType = bsmu2BearingConfigDetail.getBeeringType().getBytes();

        byte[] bearingShaftDiameter = Hex.float2ByteArray(Float.parseFloat(bsmu2BearingConfigDetail.getBearingShaftDiameter()));
        byte[] bearingOutDiameter = Hex.float2ByteArray(Float.parseFloat(bsmu2BearingConfigDetail.getBearingOutDiameter()));
        byte[] bearingMidDiameter = Hex.float2ByteArray(Float.parseFloat(bsmu2BearingConfigDetail.getBearingMidDiameter()));
        byte[] rollerDiameter = Hex.float2ByteArray(Float.parseFloat(bsmu2BearingConfigDetail.getRollerDiameter()));
        byte[] numRoller = Hex.intToByteArray(Integer.parseInt(bsmu2BearingConfigDetail.getNumRoller()));
        byte[] rowRoller = Hex.intToByteArray(Integer.parseInt(bsmu2BearingConfigDetail.getRowRoller()));
        byte[] contactAngle = Hex.float2ByteArray(Float.parseFloat(bsmu2BearingConfigDetail.getContactAngle()));
        byte[] ratioNeighbor = Hex.float2ByteArray(Float.parseFloat(bsmu2BearingConfigDetail.getRatioNeighbor()));
        byte[] ratioWheel = Hex.float2ByteArray(Float.parseFloat(bsmu2BearingConfigDetail.getRatioWheel()));
        byte[] numLargeTeeth = Hex.intToByteArray(Integer.parseInt(bsmu2BearingConfigDetail.getNumLargeTeeth()));
        byte[] numSmallTeeth = Hex.intToByteArray(Integer.parseInt(bsmu2BearingConfigDetail.getNumSmallTeeth()));

        byte[] mev_outCage = Hex.float2ByteArray(Float.parseFloat(bsmu2BearingConfigDetail.getMev_outCage()));
        byte[] mev_inCage = Hex.float2ByteArray(Float.parseFloat(bsmu2BearingConfigDetail.getMev_inCage()));
        byte[] mev_outRing = Hex.float2ByteArray(Float.parseFloat(bsmu2BearingConfigDetail.getMev_outRing()));
        byte[] mev_inRing = Hex.float2ByteArray(Float.parseFloat(bsmu2BearingConfigDetail.getMev_inRing()));
        byte[] mev_singleRoller = Hex.float2ByteArray(Float.parseFloat(bsmu2BearingConfigDetail.getMev_singleRoller()));
        byte[] mev_doubleRoller = Hex.float2ByteArray(Float.parseFloat(bsmu2BearingConfigDetail.getMev_doubleRoller()));
        byte[] mev_gear_tread = Hex.float2ByteArray(Float.parseFloat(bsmu2BearingConfigDetail.getMev_gear_tread()));
        byte[] mev_nearTeeth = Hex.float2ByteArray(Float.parseFloat(bsmu2BearingConfigDetail.getMev_nearTeeth()));

        byte[] th_tempRiseWarning = Hex.intToByteArray(Integer.parseInt(bsmu2BearingConfigDetail.getTh_tempRiseWarning()));
        byte[] th_tempRiseAlarm = Hex.intToByteArray(Integer.parseInt(bsmu2BearingConfigDetail.getTh_tempRiseAlarm()));
        byte[] th_tempWarning = Hex.intToByteArray(Integer.parseInt(bsmu2BearingConfigDetail.getTh_tempWarning()));
        byte[] th_tempAlarm = Hex.intToByteArray(Integer.parseInt(bsmu2BearingConfigDetail.getTh_tempAlarm()));
        byte[] th_outCageAlarm1 = Hex.intToByteArray(Integer.parseInt(bsmu2BearingConfigDetail.getTh_outCageAlarm1()));
        byte[] th_outCageAlarm2 = Hex.intToByteArray(Integer.parseInt(bsmu2BearingConfigDetail.getTh_outCageAlarm2()));
        byte[] th_outCageAlarm3 = Hex.intToByteArray(Integer.parseInt(bsmu2BearingConfigDetail.getTh_outCageAlarm3()));
        byte[] th_inCageAlarm1 = Hex.intToByteArray(Integer.parseInt(bsmu2BearingConfigDetail.getTh_inCageAlarm1()));
        byte[] th_inCageAlarm2 = Hex.intToByteArray(Integer.parseInt(bsmu2BearingConfigDetail.getTh_inCageAlarm2()));
        byte[] th_inCageAlarm3 = Hex.intToByteArray(Integer.parseInt(bsmu2BearingConfigDetail.getTh_inCageAlarm3()));
        byte[] th_outRingAlarm1 = Hex.intToByteArray(Integer.parseInt(bsmu2BearingConfigDetail.getTh_outRingAlarm1()));
        byte[] th_outRingAlarm2 = Hex.intToByteArray(Integer.parseInt(bsmu2BearingConfigDetail.getTh_outRingAlarm2()));
        byte[] th_outRingAlarm3 = Hex.intToByteArray(Integer.parseInt(bsmu2BearingConfigDetail.getTh_outRingAlarm3()));
        byte[] th_inRingAlarm1 = Hex.intToByteArray(Integer.parseInt(bsmu2BearingConfigDetail.getTh_inRingAlarm1()));
        byte[] th_inRingAlarm2 = Hex.intToByteArray(Integer.parseInt(bsmu2BearingConfigDetail.getTh_inRingAlarm2()));
        byte[] th_inRingAlarm3 = Hex.intToByteArray(Integer.parseInt(bsmu2BearingConfigDetail.getTh_inRingAlarm3()));
        byte[] th_singleRollerAlarm1 = Hex.intToByteArray(Integer.parseInt(bsmu2BearingConfigDetail.getTh_singleRollerAlarm1()));
        byte[] th_singleRollerAlarm2 = Hex.intToByteArray(Integer.parseInt(bsmu2BearingConfigDetail.getTh_singleRollerAlarm2()));
        byte[] th_singleRollerAlarm3 = Hex.intToByteArray(Integer.parseInt(bsmu2BearingConfigDetail.getTh_singleRollerAlarm3()));
        byte[] th_doubleRollerAlarm1 = Hex.intToByteArray(Integer.parseInt(bsmu2BearingConfigDetail.getTh_doubleRollerAlarm1()));
        byte[] th_doubleRollerAlarm2 = Hex.intToByteArray(Integer.parseInt(bsmu2BearingConfigDetail.getTh_doubleRollerAlarm2()));
        byte[] th_doubleRollerAlarm3 = Hex.intToByteArray(Integer.parseInt(bsmu2BearingConfigDetail.getTh_doubleRollerAlarm3()));
        byte[] th_treadAlarm1 = Hex.intToByteArray(Integer.parseInt(bsmu2BearingConfigDetail.getTh_treadAlarm1()));
        byte[] th_treadAlarm2 = Hex.intToByteArray(Integer.parseInt(bsmu2BearingConfigDetail.getTh_treadAlarm2()));
        byte[] th_treadAlarm3 = Hex.intToByteArray(Integer.parseInt(bsmu2BearingConfigDetail.getTh_treadAlarm3()));
        byte[] th_nearGearAlarm1 = Hex.intToByteArray(Integer.parseInt(bsmu2BearingConfigDetail.getTh_nearGearAlarm1()));
        byte[] th_nearGearAlarm2 = Hex.intToByteArray(Integer.parseInt(bsmu2BearingConfigDetail.getTh_nearGearAlarm2()));
        byte[] th_nearGearAlarm3 = Hex.intToByteArray(Integer.parseInt(bsmu2BearingConfigDetail.getTh_nearGearAlarm3()));

        byte[] minSpeed_kmh = Hex.intToByteArray(Integer.parseInt(bsmu2BearingConfigDetail.getMinSpeed_kmh()));
        byte[] demudulateGainCoef = Hex.float2ByteArray(Float.parseFloat(bsmu2BearingConfigDetail.getDemudulateGainCoef()));

        byte[] speedMultiFreqCoef = Hex.fromUnsignedInt(Long.parseLong(bsmu2BearingConfigDetail.getSpeedMultiFreqCoef()));
        byte[] highPassFilterGain = Hex.fromUnsignedInt(Long.parseLong(bsmu2BearingConfigDetail.getHighPassFilterGain()));
        byte[] bandPassFilterGain = Hex.fromUnsignedInt(Long.parseLong(bsmu2BearingConfigDetail.getBandPassFilterGain()));
        byte[] detectFilterGain = Hex.fromUnsignedInt(Long.parseLong(bsmu2BearingConfigDetail.getDetectFilterGain()));
        byte[] lowPassFilterGain = Hex.fromUnsignedInt(Long.parseLong(bsmu2BearingConfigDetail.getLowPassFilterGain()));
        byte[] lowPassFilterGain_8 = Hex.fromUnsignedInt(Long.parseLong(bsmu2BearingConfigDetail.getLowPassFilterGain_8()));
        byte[] lowPassFilterGain_58 = Hex.fromUnsignedInt(Long.parseLong(bsmu2BearingConfigDetail.getLowPassFilterGain_58()));
        byte[] lowPassFilterFreq_58 = Hex.fromUnsignedInt(Long.parseLong(bsmu2BearingConfigDetail.getLowPassFilterFreq_58()));
        byte[] originalInterval = Hex.fromUnsignedInt(Long.parseLong(bsmu2BearingConfigDetail.getOriginalInterval()));
        byte[] packSizeEachTime = Hex.fromUnsignedInt(Long.parseLong(bsmu2BearingConfigDetail.getPackSizeEachTime()));
        byte[] packSizeAll = Hex.fromUnsignedInt(Long.parseLong(bsmu2BearingConfigDetail.getPackSizeAll()));
        byte[] gainCoef_58 = Hex.fromUnsignedInt(Long.parseLong(bsmu2BearingConfigDetail.getGainCoef_58()));
        byte[] numFFTPoint = Hex.fromUnsignedInt(Long.parseLong(bsmu2BearingConfigDetail.getNumFFTPoint()));
        byte[] isSimulateSpeed = Hex.fromUnsignedInt(Long.parseLong(bsmu2BearingConfigDetail.getIsSimulateSpeed()));
        byte[] originalSampleRate = Hex.fromUnsignedInt(Long.parseLong(bsmu2BearingConfigDetail.getOriginalSampleRate()));
        byte[] originalSampleDuration = Hex.fromUnsignedInt(Long.parseLong(bsmu2BearingConfigDetail.getOriginalSampleDuration()));
        byte[] originalSampleLabel = Hex.fromUnsignedInt(Long.parseLong(bsmu2BearingConfigDetail.getOriginalSampleLabel()));

        byte[] res = Hex.mergeBytes(
                bearingType,
                bearingShaftDiameter,
                bearingOutDiameter,
                bearingMidDiameter,
                rollerDiameter,
                numRoller,
                rowRoller,
                contactAngle,
                ratioNeighbor,
                ratioWheel,
                numLargeTeeth,
                numSmallTeeth,
                mev_outCage,
                mev_inCage,
                mev_outRing,
                mev_inRing,
                mev_singleRoller,
                mev_doubleRoller,
                mev_gear_tread,
                mev_nearTeeth,
                th_tempRiseWarning,
                th_tempRiseAlarm,
                th_tempWarning,
                th_tempAlarm,
                th_outCageAlarm1,
                th_outCageAlarm2,
                th_outCageAlarm3,
                th_inCageAlarm1,
                th_inCageAlarm2,
                th_inCageAlarm3,
                th_outRingAlarm1,
                th_outRingAlarm2,
                th_outRingAlarm3,
                th_inRingAlarm1,
                th_inRingAlarm2,
                th_inRingAlarm3,
                th_singleRollerAlarm1,
                th_singleRollerAlarm2,
                th_singleRollerAlarm3,
                th_doubleRollerAlarm1,
                th_doubleRollerAlarm2,
                th_doubleRollerAlarm3,
                th_treadAlarm1,
                th_treadAlarm2,
                th_treadAlarm3,
                th_nearGearAlarm1,
                th_nearGearAlarm2,
                th_nearGearAlarm3,
                minSpeed_kmh,
                demudulateGainCoef,
                speedMultiFreqCoef,
                highPassFilterGain,
                bandPassFilterGain,
                detectFilterGain,
                lowPassFilterGain,
                lowPassFilterGain_8,
                lowPassFilterGain_58,
                lowPassFilterFreq_58,
                originalInterval,
                packSizeEachTime,
                packSizeAll,
                gainCoef_58,
                numFFTPoint,
                isSimulateSpeed,
                originalSampleRate,
                originalSampleDuration,
                originalSampleLabel

        );

        bsmu2BearingConfigService.save(new BSMU2BearingConfig(new Timestamp(System.currentTimeMillis()),res));

        return Result.succ(res.length);
    }

    @Override
    public Result close() throws IOException {
        server.close();
        return Result.succ("关闭成功");
    }
}

class ServerThread {

    public DataService dataService;
    public BasicDataService basicDataService;
    public BSMU1DataService bsmu1DataService;
    public BSMU2DataService bsmu2DataService;
    public VDPMConfigService vdpmConfigService;
    public BSMU1BasicConfigService bsmu1BasicConfigService;
    public BSMU1BearingConfigService bsmu1BearingConfigService;
    public BSMU2BasicConfigService bsmu2BasicConfigService;
    public BSMU2BearingConfigService bsmu2BearingConfigService;


    public short order;

    public ServerThread(DataService dataService,
                        BasicDataService basicDataService,
                        BSMU1DataService bsmu1DataService,
                        BSMU2DataService bsmu2DataService,
                        VDPMConfigService vdpmConfigService,
                        BSMU1BasicConfigService bsmu1BasicConfigService,
                        BSMU1BearingConfigService bsmu1BearingConfigService,
                        BSMU2BasicConfigService bsmu2BasicConfigService,
                        BSMU2BearingConfigService bsmu2BearingConfigService,
                        short order) {
        this.dataService = dataService;
        this.basicDataService = basicDataService;
        this.bsmu1DataService = bsmu1DataService;
        this.bsmu2DataService = bsmu2DataService;
        this.vdpmConfigService = vdpmConfigService;
        this.bsmu1BasicConfigService = bsmu1BasicConfigService;
        this.bsmu1BearingConfigService = bsmu1BearingConfigService;
        this.bsmu2BasicConfigService = bsmu2BasicConfigService;
        this.bsmu2BearingConfigService = bsmu2BearingConfigService;
        this.order = order;
    }

}

