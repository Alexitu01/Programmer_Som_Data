package Lectures.Lec05.TypedFun;

import java.util.ArrayList;
import java.util.List;

public class merge {

    public static void main(String[] args){

        System.out.println(mergeTwoLists(new int[]{1,3,5,7,9}, new int[]{2,4,6,8,10}));
    }

    public static List<Integer> mergeTwoLists(int[] list1, int[] list2) {
        List<Integer> mergedList = new ArrayList<>();
        int i = 0, j = 0;
        while(i < list1.length && j < list2.length) {
            if(list1[i] <= list2[j]) {
                mergedList.add(list1[i]);
                i++;
            } else {
                mergedList.add(list2[j]);
                j++;            
            }
        }

        //If one the lists is not empty, the remaining elements of that list is added to the mergedList
        if(i < list1.length) {
            while(i < list1.length) {
                mergedList.add(list1[i]);
                i++;
            }
        } else if(j < list2.length) {
            while(j < list2.length) {
                mergedList.add(list2[j]);
                j++;
            }
        }

        return mergedList;
    }
}
