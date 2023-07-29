import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.*;
/**
 * Creates a world which can be used to visualize sorting methods by arranging "blocks" on 
 * the screen.  Each block is it's own object and has a value associated with it.  This value
 * is represented visually by it's width and a number display.
 * 
 * This might help you when programming - https://docs.oracle.com/javase/8/docs/api/java/util/List.html
 * 
 * 
 * @authors C. Brooks-Prenger & Fares Alterawi
 * @version 3.0
 */
public class SortingVisualization extends World
{
    int maxOfRange = 100;       //Set the block values to range between 0 and 100 (exclusive); 
    int numNewBlocks = 100; //Note, 133 blocks maximum. Adding 134 blocks or more causes problems...bug in my code or a greenfoot limitation? Not sure and don't have the time to find out 
    
    int sizeBlock = this.getHeight()/numNewBlocks; 
    
    List blockList;  //List containing all of the block objects
    
    boolean debug = true;   //Shows extra info and debugging statements if true.
    int sorter = 0;
    int randomizer = 0;
    
    SortButton sortButton = new SortButton();
    RandomizeButton randomizeButton = new RandomizeButton();
    
    public void act()
    /** 
     * act runs every frame, all the logic for the sorter is done in here 
     * sorter switch is just the logic for the sortbutton, intiliizes the button then checks if its pressed
     * the same is true for the randomzier button
     */
    {   
        switch(sorter){
            case 0:                //initilization for sort button on screen
            
                sortInitializer();
                
                break;   
            case 1:                //sort button logic   
            
                sortButton();       
                
                break;  
        }

        switch(randomizer){
            case 0:                //initilization for randomizer button on screen        
            
                randomizeInitializer();
                
                break;   
                
            case 1:                //randomizer button logic       
            
                randomizeButton();
                
                break;
        }
        
        displayList();
    }
    
    /**
     * Constructor for objects of class SortingVisualization.
     */
    public SortingVisualization()
    {    
        // Create a new world with 1000x400 cells with a cell size of 1x1 pixels.
        super(1000, 400, 1); 

        //Create block objects and assign them to a list
        blockList = new ArrayList();
        
        for (int i = 0; i < numNewBlocks; i++) {
            blockList.add(new Block(sizeBlock,getWidth()/2,Greenfoot.getRandomNumber(maxOfRange),maxOfRange,debug)); 
            addObject( (Actor)blockList.get( blockList.size()-1 ),  getWidth(), (i*sizeBlock)+(sizeBlock/2) );
        }
        
        displayList();
    }
    
    /** 
     * adds the sort button the the screen and moves onto the next case
     */
    public void sortInitializer()
    {   
        sortButton = new SortButton();
        addObject(sortButton,850,200);      //initializes the button on the screen
        sorter = 1;     //is seperate to the button code so the button can be pressed multiple times without adding button to screen everytime
    }
    
    /** 
     * adds the randomize button the the screen and moves onto the next case
     */
    public void randomizeInitializer()
    {   
        randomizeButton = new RandomizeButton();
        addObject(randomizeButton,150,200);     //initializes the button on the screen
        randomizer = 1;     //is seperate to the button code so the button can be pressed multiple times without adding button to screen everytime                
    }
     
    /** 
     * if the sortbutton is clicked a parellel thread starts, the sorting is done in the parellel thread
     */
    public void sortButton()
    {
        if (sortButton.getIsClicked()) 
        {
            Thread thread = new Thread(){          // a parellel thread is used so when Thread.sleap is called, the parellel program is paused and not the main one
                /** 
                 * just sorts the blocklist
                 */
                public void run(){
                      blockList = mergeSort(blockList);  //blockList is now equal to the sorted blocklist, in order for the screen to display the sort this has to be done
                    }
            };
                thread.start();  
                
                sorter = 2; //sets sorter to 2 so the switch statement in act doesn't try to call sort every frame
                randomizeButton.isClicked = false; //just done so the randomize button can be clicked after the blocks are sorted
                randomizer = 1; 
        }
    }
    
    
    /** 
     * when the randomize button is clicked all the blocks on the screen are deleted and replaced with the new randomzied ones 
     */
    public void randomizeButton()
    {
        sizeBlock = this.getHeight()/blockList.size();
        if (randomizeButton.getIsClicked())     
        {   
            for (int i = 0; i <blockList.size();i++){           //clears the blocks from the screen
                removeObject((Block)blockList.get(i));
            }
            
            blockList.clear();            //clears the elements in block list
            
            for (int i = 0; i < numNewBlocks; i++) {      //your code, in this case it just randomizes and assigns blocks to the list after screen is cleared
                blockList.add(new Block(sizeBlock,getWidth()/2,Greenfoot.getRandomNumber(maxOfRange),maxOfRange,debug)); 
                addObject((Actor)blockList.get( blockList.size()-1),  getWidth(), (i*sizeBlock)+(sizeBlock/2) );
            }
            randomizer = 2;   //moves randomzier to 2
            sortButton.isClicked = false;       //just done so the sort button can be clicked after the blocks are randomized
            sorter = 1;
        }
    }
    
    /** 
     * Literally just manually replaces the sub lists elements with the sorted sub lists elements
     * @param subList this is the first parameter for the deepcopy method
     * @param sortedSublsit this is the second parameter for the deepcopy method
     */

    static void deepCopy (List sublist, List sortedSublist)
    {
        for(int i = 0; i < sublist.size(); i++)
        {
            sublist.set(i, sortedSublist.get(i));  //replaces the elements in the sublist with the elements in the sorted sublist
        }
    }
    
    /** 
     * Makes a sublist of the main list that references the same memory location as the first half of the main list
     * Same is true for the second sublist
     * Makes a sorted copy of the sublists that is deepcopied into the original sublists, thereby updating the main list
     * merges the 2 sorted sublists 
     * @param blockList this is the only parameter for the mergeSort method
     * @return blockList this returns the list when the base case is true
     * @return List this returns a list of the 2 merged sublists
     */
    
    public List mergeSort(List blockList)
    {
        if (blockList.size() == 1)     //basecase, the blocklists is onlt returned when its size is 1, so the seperation is not showed but the sorting is 
        {
            return blockList;
        }
        
        int middle = blockList.size()/2;
      
        List firstHalfOfBlockList = blockList.subList(0, middle); // Shallow copy, refrences the memory location of the first half of the blocklist
        List secondHalfOfBlockList = blockList.subList(middle, blockList.size()); // Shallow copy
        
        List sortedFirstHalf = mergeSort(firstHalfOfBlockList); // makes a new list in memory, because I'm initializing the list to the return value of mergeSort
        List sortedSecondHalf = mergeSort(secondHalfOfBlockList);// makes a new list in memory
        
        //deep copy the sorted sublists into the original sublist. This way, we will change the original blocklist, since the original sublist was a shallow copy of blocklist
        deepCopy(firstHalfOfBlockList, sortedFirstHalf);
        deepCopy(secondHalfOfBlockList, sortedSecondHalf);
        //Now, the original blockList will be updated
        
        
        try {
          Thread.sleep(50);     //pauses the program but is only called in the parellel thread, so it just pauses the parellel thread rather than the main one 
        }
        catch(Exception e) {
            System.out.println("error");
        }
        
        return merge(firstHalfOfBlockList, secondHalfOfBlockList); //merging the lists

    }
    
    /** 
     * merges 2 lists into the final list, checks which value in each sublist is smaller
     * then adds that value to the final list at the counter
     * @param list 1 this is the first parameter for the mergeSort methods
     * @param list 2 this is the second parameter for the mergeSort methods
     * @return List this returns the final merged list
     */

    private List merge(List list1, List list2)
    {
        int finalSize = list1.size() + list2.size();
        List finalList = new ArrayList(finalSize);        //initializing a new list
        for (int i = 0; i < finalSize; i++) {             //giving the list null elements,that way the while loop actually runs
            finalList.add(null);
        }
        
        int pointer1 = 0, pointer2 = 0, counter = 0;
    
        while(counter < finalSize)  //as long as the loop isn't iterated through more than the final list size, the loop runs
        {
            if (pointer1 < list1.size() && (pointer2 == list2.size() || ((Block)list1.get(pointer1)).getValue() < ((Block)list2.get(pointer2)).getValue() )) 
            {  
                    //as long as pointer1 is less than the size of the list
                    //and pointer 2 is at the end of the second list or the value in list 1 at pointer1  is less than the value in list 2 at pointer 2  
                finalList.set(counter, list1.get(pointer1)); 
                pointer1++;      
                    // pointer 1 increase because the element of the list at pointer 1 is less than the element of the list at pointer 2
                    // so it increases the pointer 1 value 
                    // so the loop can again check if the value in list 1 is greater than or less than the value at list 2 and sort the list accordingly
            }
            else //if the first conditional is not true this has to be true so the opposite commands are set in else
            {
                finalList.set(counter, list2.get(pointer2));
                pointer2++; 
            
            }
            
            counter++; //increases the counter everytime the loop is iterated
        }
        
        return finalList;
    }
    
    /**
     * Display the list of blocks on the screen in same order as they are stored in the list
     */
    public void displayList() {
        sizeBlock = this.getHeight()/blockList.size();
        for (int i = 0; i < blockList.size(); i++) {
            Block currentBlock = (Block)blockList.get(i);
            currentBlock.setLocation(getWidth()/2, (i*sizeBlock)+(sizeBlock/2) );
        }
        
    }
    
    /**
     * Return the max value that the block are able to have
     * @return The maximum integer value the blocks are allowed to have
     */
    public int getMaxOfRange() {
        return maxOfRange;
    }
    
    /**
     * Return the list of blocks
     * @return The list of blocks to be sorted
     */
    public List getBlockList() {
        return blockList;
    }
    
    
    
}
