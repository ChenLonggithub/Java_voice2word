package com.iflytek.voicecloud.lfasr.demo.util;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.iflytek.voicecloud.lfasr.demo.dto.ResultDto;

import java.lang.reflect.Type;
import java.util.List;

public class Main {



    public static <T> List<T> changeGsonToList(String gsonString, Class<T> cls) {
        Gson gson = new Gson();
        List<T> list = gson.fromJson(gsonString, new TypeToken<List<T>>() {
        }.getType());
        return list;
    }

    public static void main1(String[] args) {
        String  string = "[{\"bg\":\"2310\",\"ed\":\"3850\",\"onebest\":\"嗯\",\"speaker\":\"0\"},{\"bg\":\"7250\",\"ed\":\"8860\",\"onebest\":\"嗯。\",\"speaker\":\"0\"},{\"bg\":\"10820\",\"ed\":\"12590\",\"onebest\":\"唉张总，您好！\",\"speaker\":\"0\"},{\"bg\":\"13070\",\"ed\":\"16400\",\"onebest\":\"你好，啊我是深圳大管家的海军。\",\"speaker\":\"0\"},{\"bg\":\"16400\",\"ed\":\"17760\",\"onebest\":\"是张总张总。\",\"speaker\":\"0\"},{\"bg\":\"17760\",\"ed\":\"26630\",\"onebest\":\"我们现在针对贵公司的实施行业呢有一个项目进度管理和客户关系管理系统，想邀请郑总您这边来了解一下吧。\",\"speaker\":\"0\"},{\"bg\":\"27930\",\"ed\":\"29200\",\"onebest\":\"什么东西！\",\"speaker\":\"0\"},{\"bg\":\"29210\",\"ed\":\"36230\",\"onebest\":\"啊就我们这边是做智能化企业管理系统的，主要是针对公司内部人员管理这方面，\",\"speaker\":\"0\"},{\"bg\":\"36230\",\"ed\":\"43470\",\"onebest\":\"它可以帮助您去解决一些项目技术管理、客户管理以及智能绩效管理这方面的问题的。\",\"speaker\":\"0\"},{\"bg\":\"43540\",\"ed\":\"47390\",\"onebest\":\"啊这种您之前是有了解过企业管理系统的吗？\",\"speaker\":\"0\"},{\"bg\":\"47940\",\"ed\":\"49450\",\"onebest\":\"我不需要！\",\"speaker\":\"0\"},{\"bg\":\"49460\",\"ed\":\"54650\",\"onebest\":\"啊那要不我先加一下您的微信，把我们的资料发给您，先了解一下了。\",\"speaker\":\"0\"},{\"bg\":\"54650\",\"ed\":\"57040\",\"onebest\":\"后续有需要的话我们可以再联系嘛。\",\"speaker\":\"0\"},{\"bg\":\"57410\",\"ed\":\"60900\",\"onebest\":\"嗯这个手机号可以加您的微信吗！\",\"speaker\":\"0\"}]";

        Type listType = new TypeToken<List<ResultDto>>() {}.getType();
        List<ResultDto> results = new Gson().fromJson(string, listType);

        String s = "";
        for (ResultDto result : results) {
            s = s+result.getOnebest()+",";
        }

        System.out.println(s);

    }

    public static void main(String[] args) {

        int x = 5;
        int i = get(x);
        System.out.println(i);
    }

    private static int get(int x) {
        switch (x){
            case 1:
                return 1;
            case 2:
                return 2;
            case 5:
                return 5;
            default:
                return 3;
        }
    }
}
