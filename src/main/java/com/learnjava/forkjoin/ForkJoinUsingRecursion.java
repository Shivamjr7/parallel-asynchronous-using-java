package com.learnjava.forkjoin;

import com.learnjava.util.DataSet;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

import static com.learnjava.util.CommonUtil.delay;
import static com.learnjava.util.CommonUtil.stopWatch;
import static com.learnjava.util.LoggerUtil.log;

public class ForkJoinUsingRecursion extends RecursiveTask<List<String>> {

    private List<String> names;

    ForkJoinUsingRecursion(List<String> names) {
        this.names = names;
    }

    public static void main(String[] args) {

        stopWatch.start();
        List<String> resultList = new ArrayList<>();
        List<String> names = DataSet.namesList();
        //create a Fork Join pool
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        //creating task
        ForkJoinUsingRecursion forkJoinUsingRecursion = new ForkJoinUsingRecursion(names);

        //submit the task to fork join pool , task will be added to shared queue
        resultList= forkJoinPool.invoke(forkJoinUsingRecursion);
        stopWatch.stop();
        log("Final Result : " + resultList);
        log("Total Time Taken : " + stopWatch.getTime());
    }


    private static String addNameLengthTransform(String name) {
        delay(500);
        return name.length() + " - " + name;
    }

    @Override
    protected List<String> compute() {
        //break condition
        if (names.size() <= 1) {
            List<String> result = new ArrayList<>();
            names.forEach(name -> result.add(addNameLengthTransform(name)));
            return result;
        }

        //break the list to 2
        int mid = names.size() / 2;

        ForkJoinTask<List<String>> leftList = new ForkJoinUsingRecursion(names.subList(0, mid)).fork();
        //update the list
        names = names.subList(mid, names.size());
        List<String> rightResult = compute();
        List<String> leftResult = leftList.join();
        leftResult.addAll(rightResult);
        return leftResult;
    }
}
