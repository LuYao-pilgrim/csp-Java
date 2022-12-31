package com.luyao.vehicle.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/download")
public class RowFileController {

    @RequestMapping(value = "/row/downFile1")
    public byte[] downloadFile1(@RequestParam String name) throws IOException {
        // 要下载的文件的全路径名
        String filePath ="/root/savedata/bsmu1/bearing/"+name;
        File file = new File(filePath);
        byte[] res = Files.readAllBytes(file.toPath());

        return res;
    }

    @RequestMapping(value = "/row/downFile2")
    public byte[] downloadFile2(@RequestParam String name) throws IOException {
        // 要下载的文件的全路径名
        String filePath ="/root/savedata/bsmu1/hunting/"+name;
        File file = new File(filePath);
        byte[] res = Files.readAllBytes(file.toPath());

        return res;
    }

    @RequestMapping(value = "/row/downFile3")
    public byte[] downloadFile3(@RequestParam String name) throws IOException {
        // 要下载的文件的全路径名
        String filePath ="/root/savedata/bsmu2/bearing/"+name;
        File file = new File(filePath);
        byte[] res = Files.readAllBytes(file.toPath());

        return res;
    }

    @RequestMapping(value = "/row/downFile4")
    public byte[] downloadFile4(@RequestParam String name) throws IOException {
        // 要下载的文件的全路径名
        String filePath ="/root/savedata/bsmu2/hunting/"+name;
        File file = new File(filePath);
        byte[] res = Files.readAllBytes(file.toPath());

        return res;
    }


    @RequestMapping("/row/filenames1")
    public List<String> getRowFileNames1() {
        List<String> fileNameList = new ArrayList<>();
        File file = new File("/root/savedata/bsmu1/bearing");
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (int i = 0; i < files.length; i++) {
                fileNameList.add(files[i].getName());
            }
        }
        return fileNameList;
    }

    @RequestMapping("/row/filenames2")
    public List<String> getRowFileNames2() {
        List<String> fileNameList = new ArrayList<>();
        File file = new File("/root/savedata/bsmu1/hunting");
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (int i = 0; i < files.length; i++) {
                fileNameList.add(files[i].getName());
            }
        }
        return fileNameList;
    }

    @RequestMapping("/row/filenames3")
    public List<String> getRowFileNames3() {
        List<String> fileNameList = new ArrayList<>();
        File file = new File("/root/savedata/bsmu2/bearing");
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (int i = 0; i < files.length; i++) {
                fileNameList.add(files[i].getName());
            }
        }
        return fileNameList;
    }

    @RequestMapping("/row/filenames4")
    public List<String> getRowFileNames4() {
        List<String> fileNameList = new ArrayList<>();
        File file = new File("/root/savedata/bsmu2/hunting");
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (int i = 0; i < files.length; i++) {
                fileNameList.add(files[i].getName());
            }
        }
        return fileNameList;
    }
}
