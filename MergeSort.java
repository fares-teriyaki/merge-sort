import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
/**
 * original code with arrays rather than lists 
 * 
 * @author Fares Alterawi
 * @version 1.0
 */
public class MergeSort extends Actor
{
    /**
    public int[] mergeSort(int[] list)
    {
        if (list.length == 1)
        {
            return list;
        }
        int middle = list.length/2;
        
        int[] list1 = Arrays.copyOfRange(list, 0, list.length/2);
        int[] list2 = Arrays.copyOfRange(list, list.length/2, list.length);
        
        return merge(mergeSort(list1), mergeSort(list2));
    }
    
    private int[] merge(int[] list1, int[] list2)
    {
        int [] finalList = new int [list1.length + list2.length];
        int pointer1 = 0, pointer2 = 0, counter = 0;
        while(counter < finalList.length)
        {
            if (pointer1 < list1.length && (pointer2 == list2.length || list1[pointer1] < list2[pointer2]))
            {
                finalList[counter] = list1[pointer1];
                pointer1++;
            }
            else 
            {
                finalList[counter] = list2[pointer2];
                pointer2++; 
            }
            counter++;
        }
        return finalList;
    }
    */
    //this is the merge sort algorithim with arrays rather than lists, I changed the syntax later to work with lists
}
