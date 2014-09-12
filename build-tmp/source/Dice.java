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


PFont f;
//Die d0,d1,d2;

//Die[] die_array = new Die[]

//int number_of_dice = 3;
//Die[] die_array = new Die [number_of_dice+1];
 Die[] die_array;

int s_to_dice_num;
int text_height_val = 20;
int text_out_y = 0;
int text_out_x = 0;

public void setup()
{
	textSize(18);
	f = createFont("Arial",16,true); 
	/*size(300,128);
	d0 = new Die(20,20,60);
	d1 = new Die(120,20,60);
	d2 = new Die(220,20,60);*/
	init_size_and_dice(800,500,16,2);

	noLoop();
}
public void draw()
{
	background(0);
	/*d0.roll();
	d1.roll();
	d2.roll();
	d0.show();
	d1.show();
	d2.show();*/
	roll_show_all_dice();

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
	Die(int x, int y, int w_h) //constructor
	{
		//variable initializations here
		corner_x = x;
		corner_y = y;
		Die_dot_bits = 0;
		Die_val = 0;
		Die_width = w_h;
		Die_dot_width = (((w_h>>2)>>2)*3);
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

public void init_size_and_dice(int s_width, int s_height, int d_w_h, int d_space){

	size(s_width,s_height + text_height_val);
	text_out_x = (s_width>>1);
	text_out_y = s_height + text_height_val;
	int s_dice_grid_h = s_height/(d_w_h+d_space);
	int s_dice_grid_w = s_width/(d_w_h+d_space);
	s_to_dice_num = (s_dice_grid_w * s_dice_grid_h);
	die_array = new Die [s_to_dice_num+1];
	int d_cnt = 0;
	int d_y_cor = 0;
	int d_x_cor = 0;

	for(int d_y=0;d_y<s_dice_grid_h;d_y++){
		d_y_cor = d_y*(d_w_h+d_space);

		for(int d_x=0;d_x<s_dice_grid_w;d_x++){
			d_x_cor = d_x*(d_w_h+d_space);

			


			die_array[d_cnt] = new Die(d_x_cor,d_y_cor,d_w_h);
			d_cnt++;

		}

	}


}

public void roll_show_all_dice(){
	int roll_total_r=0;
	for(int d_i=0;d_i<(die_array.length-1) ; d_i++){

  //textFont(f);
		die_array[d_i].roll();
		die_array[d_i].show();

		roll_total_r += die_array[d_i].Die_val;

  //textFont(f);
		
	}
fill(255);
		char out_total_num_text = PApplet.parseChar(roll_total_r);
		//text(out_total_num_text,text_out_x,text_out_y);
		text("total : "  + roll_total_r, text_out_x-80,text_out_y);

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
