import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import g4p_controls.*; 
import processing.serial.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class arduino_due_dac extends PApplet {





int[] [] data = {
  // Sin wave
  {
    0x7ff, 0x86a, 0x8d5, 0x93f, 0x9a9, 0xa11, 0xa78, 0xadd, 0xb40, 0xba1,
    0xbff, 0xc5a, 0xcb2, 0xd08, 0xd59, 0xda7, 0xdf1, 0xe36, 0xe77, 0xeb4,
    0xeec, 0xf1f, 0xf4d, 0xf77, 0xf9a, 0xfb9, 0xfd2, 0xfe5, 0xff3, 0xffc,
    0xfff, 0xffc, 0xff3, 0xfe5, 0xfd2, 0xfb9, 0xf9a, 0xf77, 0xf4d, 0xf1f,
    0xeec, 0xeb4, 0xe77, 0xe36, 0xdf1, 0xda7, 0xd59, 0xd08, 0xcb2, 0xc5a,
    0xbff, 0xba1, 0xb40, 0xadd, 0xa78, 0xa11, 0x9a9, 0x93f, 0x8d5, 0x86a,
    0x7ff, 0x794, 0x729, 0x6bf, 0x655, 0x5ed, 0x586, 0x521, 0x4be, 0x45d,
    0x3ff, 0x3a4, 0x34c, 0x2f6, 0x2a5, 0x257, 0x20d, 0x1c8, 0x187, 0x14a,
    0x112, 0xdf, 0xb1, 0x87, 0x64, 0x45, 0x2c, 0x19, 0xb, 0x2,
    0x0, 0x2, 0xb, 0x19, 0x2c, 0x45, 0x64, 0x87, 0xb1, 0xdf,
    0x112, 0x14a, 0x187, 0x1c8, 0x20d, 0x257, 0x2a5, 0x2f6, 0x34c, 0x3a4,
    0x3ff, 0x45d, 0x4be, 0x521, 0x586, 0x5ed, 0x655, 0x6bf, 0x729, 0x794
  }
  ,

  // Triangular wave
  {
    0x44, 0x88, 0xcc, 0x110, 0x154, 0x198, 0x1dc, 0x220, 0x264, 0x2a8,
    0x2ec, 0x330, 0x374, 0x3b8, 0x3fc, 0x440, 0x484, 0x4c8, 0x50c, 0x550,
    0x594, 0x5d8, 0x61c, 0x660, 0x6a4, 0x6e8, 0x72c, 0x770, 0x7b4, 0x7f8,
    0x83c, 0x880, 0x8c4, 0x908, 0x94c, 0x990, 0x9d4, 0xa18, 0xa5c, 0xaa0,
    0xae4, 0xb28, 0xb6c, 0xbb0, 0xbf4, 0xc38, 0xc7c, 0xcc0, 0xd04, 0xd48,
    0xd8c, 0xdd0, 0xe14, 0xe58, 0xe9c, 0xee0, 0xf24, 0xf68, 0xfac, 0xff0,
    0xfac, 0xf68, 0xf24, 0xee0, 0xe9c, 0xe58, 0xe14, 0xdd0, 0xd8c, 0xd48,
    0xd04, 0xcc0, 0xc7c, 0xc38, 0xbf4, 0xbb0, 0xb6c, 0xb28, 0xae4, 0xaa0,
    0xa5c, 0xa18, 0x9d4, 0x990, 0x94c, 0x908, 0x8c4, 0x880, 0x83c, 0x7f8,
    0x7b4, 0x770, 0x72c, 0x6e8, 0x6a4, 0x660, 0x61c, 0x5d8, 0x594, 0x550,
    0x50c, 0x4c8, 0x484, 0x440, 0x3fc, 0x3b8, 0x374, 0x330, 0x2ec, 0x2a8,
    0x264, 0x220, 0x1dc, 0x198, 0x154, 0x110, 0xcc, 0x88, 0x44, 0x0
  }
  ,

  // Sawtooth wave
  {
    0x22, 0x44, 0x66, 0x88, 0xaa, 0xcc, 0xee, 0x110, 0x132, 0x154,
    0x176, 0x198, 0x1ba, 0x1dc, 0x1fe, 0x220, 0x242, 0x264, 0x286, 0x2a8,
    0x2ca, 0x2ec, 0x30e, 0x330, 0x352, 0x374, 0x396, 0x3b8, 0x3da, 0x3fc,
    0x41e, 0x440, 0x462, 0x484, 0x4a6, 0x4c8, 0x4ea, 0x50c, 0x52e, 0x550,
    0x572, 0x594, 0x5b6, 0x5d8, 0x5fa, 0x61c, 0x63e, 0x660, 0x682, 0x6a4,
    0x6c6, 0x6e8, 0x70a, 0x72c, 0x74e, 0x770, 0x792, 0x7b4, 0x7d6, 0x7f8,
    0x81a, 0x83c, 0x85e, 0x880, 0x8a2, 0x8c4, 0x8e6, 0x908, 0x92a, 0x94c,
    0x96e, 0x990, 0x9b2, 0x9d4, 0x9f6, 0xa18, 0xa3a, 0xa5c, 0xa7e, 0xaa0,
    0xac2, 0xae4, 0xb06, 0xb28, 0xb4a, 0xb6c, 0xb8e, 0xbb0, 0xbd2, 0xbf4,
    0xc16, 0xc38, 0xc5a, 0xc7c, 0xc9e, 0xcc0, 0xce2, 0xd04, 0xd26, 0xd48,
    0xd6a, 0xd8c, 0xdae, 0xdd0, 0xdf2, 0xe14, 0xe36, 0xe58, 0xe7a, 0xe9c,
    0xebe, 0xee0, 0xf02, 0xf24, 0xf46, 0xf68, 0xf8a, 0xfac, 0xfce, 0xff0
  }
  ,

  // Square wave
  {
    0xfff, 0xfff, 0xfff, 0xfff, 0xfff, 0xfff, 0xfff, 0xfff, 0xfff, 0xfff,
    0xfff, 0xfff, 0xfff, 0xfff, 0xfff, 0xfff, 0xfff, 0xfff, 0xfff, 0xfff,
    0xfff, 0xfff, 0xfff, 0xfff, 0xfff, 0xfff, 0xfff, 0xfff, 0xfff, 0xfff,
    0xfff, 0xfff, 0xfff, 0xfff, 0xfff, 0xfff, 0xfff, 0xfff, 0xfff, 0xfff,
    0xfff, 0xfff, 0xfff, 0xfff, 0xfff, 0xfff, 0xfff, 0xfff, 0xfff, 0xfff,
    0xfff, 0xfff, 0xfff, 0xfff, 0xfff, 0xfff, 0xfff, 0xfff, 0xfff, 0xfff,
    0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0,
    0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0,
    0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0,
    0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0,
    0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0,
    0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0
  }

};
  
public class punto{
  public float x;
  public float y;
}

int dataSel=0;

int larg = 30;
int lung = 80;

int newFreq = 1000, oldFreq = 1000;

float hp = 0.78f;
float wp = 0.83f;

int spazioLibero, spazioSotto;

punto mcord = new punto();


int[] data2 = new int[120];

Serial myPort;  // Create object from Serial class

int sample = 120;

public void setup() {
  
  //fullScreen();

  
  
  spazioSotto = PApplet.parseInt(width*wp);

  spazioLibero = spazioSotto - (5 * lung);
  

  createGUI();

  String portName = Serial.list()[0];
  myPort = new Serial(this, portName, 115200);
  
  frameRate(240);
}

public void draw() {
  
  background(255);
  if(myPort.available() > 0){
  //  println("in"+myPort.readStringUntil(':'));
  }
  
  strokeWeight(4); 
  stroke(255, 0, 0);
  line(0, height*hp, width*wp, height*hp);
  line(width*wp, height*hp, width*wp, 0);
  fill(0);
  strokeWeight(1); 
  
  if(mousePressed == true){
    if(dataSel == 0){
      if((mouseX < width * wp) && (mouseY < height * hp)){
        data2[constrain(PApplet.parseInt(map(mouseX, 0, width*wp, 0, sample)), 0, sample-1)] = PApplet.parseInt(map(mouseY, 0.0f, height*hp, 4095, 0));
        //println("x  "+mouseX+"\ty  "+int(map(mouseY, 0.0f, height, 4095,0)));
      }
    }
  }
  
  
  strokeWeight(2); 
  stroke(0, 255, 0);
  
  for(int i = 0; i < sample-1; i++){
    if(dataSel == 0)
      line(PApplet.parseInt(map(i, 0, sample, 0, width*wp)), PApplet.parseInt(map(data2[i], 0, 4095, height*hp, 0)), PApplet.parseInt(map(i+1, 0, sample, 0, width*wp)), PApplet.parseInt(map(data2[i+1], 0, 4095, height*hp, 0)));
    else
      line(PApplet.parseInt(map(i, 0, sample, 0, width*wp)), PApplet.parseInt(map(data[dataSel - 1][i], 0, 4095, height*hp, 0)), PApplet.parseInt(map(i+1, 0, sample, 0, width*wp)), PApplet.parseInt(map(data[dataSel - 1][i+1], 0, 4095, height*hp, 0)));
  }
  
  strokeWeight(1); 

  
  mcord.x = mouseX;
  mcord.y = mouseY;
  fill(0);
  drawCords(mcord);//convert_pix_mm(mcord));
  
  //println(frameRate);
}

//int datas = 119;

public void keyPressed(){
  if(key == 'x')
    exit();
  /*
  myPort.write('s');
  myPort.write(""+sample+';');
  
  for(int i = 0; i < sample; i++){
    myPort.write(""+data2[i]+';');
    //println("out"+data2[i]+';');
  }*/
}

public void mouseReleased() {
  if(newFreq != oldFreq){
    oldFreq = newFreq;
    
    myPort.write('f');
    myPort.write(""+newFreq+';');
  }
  aggiorna();
  
}

public void drawCords(punto cord){
  text(cord.x+"\n"+cord.y, mouseX+10,mouseY+10);
}

public void aggiorna(){
  myPort.write('s');
  myPort.write(""+sample+';');
  
  for(int i = 0; i < sample; i++){
    if(dataSel == 0)
      myPort.write(""+data2[i]+';');
    else
      myPort.write(""+data[dataSel - 1][i]+';');
    //println("out"+data2[i]+';');
  }
}
/* =========================================================
 * ====                   WARNING                        ===
 * =========================================================
 * The code in this tab has been generated from the GUI form
 * designer and care should be taken when editing this file.
 * Only add/edit code inside the event handlers i.e. only
 * use lines between the matching comment tags. e.g.

 void myBtnEvents(GButton button) { //_CODE_:button1:12356:
     // It is safe to enter your event code here  
 } //_CODE_:button1:12356:
 
 * Do not rename this tab!
 * =========================================================
 */

public void button1_click1(GButton source, GEvent event) { //_CODE_:sinus:349860:
  println("sinus - GButton >> GEvent." + event + " @ " + millis());
  dataSel = 1;
  aggiorna();
} //_CODE_:sinus:349860:

public void button1_click2(GButton source, GEvent event) { //_CODE_:tri:377017:
  println("button1 - GButton >> GEvent." + event + " @ " + millis());
  dataSel = 2;
  aggiorna();
} //_CODE_:tri:377017:

public void button2_click1(GButton source, GEvent event) { //_CODE_:saw:881647:
  println("button2 - GButton >> GEvent." + event + " @ " + millis());
  dataSel = 3;
  aggiorna();
} //_CODE_:saw:881647:

public void button3_click1(GButton source, GEvent event) { //_CODE_:square:991442:
  println("button3 - GButton >> GEvent." + event + " @ " + millis());
  dataSel = 4;
  aggiorna();
} //_CODE_:square:991442:

public void button4_click1(GButton source, GEvent event) { //_CODE_:custom:771265:
  println("button4 - GButton >> GEvent." + event + " @ " + millis());
  dataSel = 0;
  aggiorna();
} //_CODE_:custom:771265:

public void custom_slider1_change1(GCustomSlider source, GEvent event) { //_CODE_:custom_slider1:420683:
  println("custom_slider1 - GCustomSlider >> GEvent." + event + " @ " + millis());
  newFreq = custom_slider1.getValueI();
} //_CODE_:custom_slider1:420683:



// Create all the GUI controls. 
// autogenerated do not edit
public void createGUI(){
  G4P.messagesEnabled(false);
  G4P.setGlobalColorScheme(GCScheme.BLUE_SCHEME);
  G4P.setCursor(ARROW);
  surface.setTitle("Sketch Window");
  sinus = new GButton(this, spazioLibero/6, (height * hp) + ((height - (height * hp)) / 2), lung, larg);
  sinus.setText("Sin");
  sinus.addEventHandler(this, "button1_click1");
  tri = new GButton(this, spazioLibero/3 + lung, (height * hp) + ((height - (height * hp)) / 2), lung, larg);
  tri.setText("Triangolare");
  tri.addEventHandler(this, "button1_click2");
  saw = new GButton(this, spazioLibero/2 + lung * 2, (height * hp) + ((height - (height * hp)) / 2), lung, larg);
  saw.setText("Dente di sega");
  saw.addEventHandler(this, "button2_click1");
  square = new GButton(this, spazioLibero/6*4 + lung * 3, (height * hp) + ((height - (height * hp)) / 2), lung, larg);
  square.setText("Quadra");
  square.addEventHandler(this, "button3_click1");
  custom = new GButton(this, spazioLibero/6*5 + lung * 4, (height * hp) + ((height - (height * hp)) / 2), lung, larg);
  custom.setText("Custom");
  custom.addEventHandler(this, "button4_click1");
  custom_slider1 = new GCustomSlider(this, 1300, 20, 550, 100, "grey_blue");
  custom_slider1.setShowValue(true);
  custom_slider1.setRotation(PI/2, GControlMode.CORNER);
  custom_slider1.setLimits(500.0f, 1000.0f, 0.0f);
  custom_slider1.setNumberFormat(G4P.DECIMAL, 2);
  custom_slider1.setOpaque(false);
  custom_slider1.addEventHandler(this, "custom_slider1_change1");
}

// Variable declarations 
// autogenerated do not edit
GButton sinus; 
GButton tri; 
GButton saw; 
GButton square; 
GButton custom; 
GCustomSlider custom_slider1; 
  public void settings() {  size(displayWidth, displayHeight); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "--present", "--window-color=#666666", "--stop-color=#FF0808", "arduino_due_dac" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
