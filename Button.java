import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Makes a button and checks if its clicked
 * 
 * @author Fares Alterawi
 * @version 2.0
 */
public class Button extends Actor
{
    boolean isClicked = false;
    /**
     * This method literally just returns the value of is clicked
     */
    private void checkIsClicked(){
        if (Greenfoot.mouseClicked(this)){              //if a button is pressed the boolean isClicked is set to true 
            isClicked = Greenfoot.mouseClicked(this);
        }

    }
    
    /**
     * This method literally just returns the value of is clicked
     * @ return isClicked 
     */
    public boolean getIsClicked(){         //returns the boolean value isClicked useful for if statments in MyWorld 
        return isClicked;
    }
    
    /**
     * This method constantly checks if the button is clicked
     */
    public void act(){   //checks every frame if the button is clicked
        checkIsClicked();
    }
}
