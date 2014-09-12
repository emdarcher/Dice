import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class Dice extends PApplet {

/*
die face
has 3 2-dot pairs that are always together,
and one middle dot that can be independent.
Using this format, we can store the state of one die
in a set of 4 bits. With this, you could fit two dice
states in one 8-bit byte.
0 bit - middle dot
1 bit - top left, bottom right
2 bit - top right, bottom left
3 bit - middle left and right

diagram of die face:

[1]  _  [2]
[3] [0] [3]
[2]  -  [1]

*/

//array of bytes that hold the states for different numbers
byte[] die_bits = {
	(byte)0x00,//0b0000;
	(byte)0x01,//0b0001;
	(byte)0x02,//0b0010;
	(byte)0x03,//0b0011;
	(byte)0x06,//0b0110;
	(byte)0x07,//0b0111;
	(byte)0x0E,//0b1110;
};

Die thing1;

public void setup()
{
	size(127,127);
	thing1 = new Die(20,20);
	noLoop();
}
public void draw()
{
	background(0);
	thing1.roll();
	thing1.show();
}
public void mousePressed()
{
	redraw();
}
class Die //models one single dice cube
{
	int corner_x, corner_y;
	byte Die_dot_bits;
	int  Die_val;
	int Die_width;
	int Die_dot_width;
	//variable declarations here
	Die(int x, int y) //constructor
	{
		//variable initializations here
		corner_x = x;
		corner_y = y;
		Die_dot_bits = 0;
		Die_val = 0;
		Die_width = 60;
		Die_dot_width = 15;
	}
	public void roll()
	{
		//your code here

		Die_val = (int)((Math.random()*6)+1);

	}
	public void show()
	{
		//your code here
		fill(255);
		rect(corner_x, corner_y, Die_width, Die_width);


		Die_dot_bits = die_bits[Die_val];

		fill(0);

		if((Die_dot_bits & (1<<0))!=0){
			ellipse(corner_x+(Die_width>>1), corner_y+(Die_width>>1), Die_dot_width, Die_dot_width);
		}
		if((Die_dot_bits & (1<<1))!=0){
			ellipse(corner_x+(Die_width>>2), corner_y+(Die_width>>2), Die_dot_width, Die_dot_width);
			ellipse(corner_x+((Die_width>>2)*3), corner_y+((Die_width>>2)*3), Die_dot_width, Die_dot_width);
		}
		if((Die_dot_bits & (1<<2))!=0){
			ellipse(corner_x+((Die_width>>2)*3), corner_y+((Die_width>>2)), Die_dot_width, Die_dot_width);
			ellipse(corner_x+((Die_width>>2)), corner_y+((Die_width>>2)*3), Die_dot_width, Die_dot_width);
		}
		if((Die_dot_bits & (1<<3))!=0){
			ellipse(corner_x+((Die_width>>2)), corner_y+(Die_width>>1), Die_dot_width, Die_dot_width);
			ellipse(corner_x+((Die_width>>2)*3), corner_y+(Die_width>>1), Die_dot_width, Die_dot_width);
		}


	}
}
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "Dice" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
