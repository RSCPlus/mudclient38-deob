package jagex.client;

import jagex.BZLib;
import jagex.Utility;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Component;
import java.awt.Event;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.IndexColorModel;
import java.awt.image.MemoryImageSource;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

public class GameApplet extends Applet implements Runnable {
   private int appletWidth = 512;
   private int appletHeight = 384;
   private Thread appletThread;
   private int targetFPS = 20;
   private int maxDrawTime = 1000;
   private long[] timings = new long[10];
   static GameFrame gameFrame = null;
   private boolean appletMode;
   private int stopTimeout;
   private int interlaceTimer;
   public int rp;
   public int lastMouseAction;
   public int loadingStep = 1;
   public String up;
   private boolean vp = false;
   private int loadingProgressPercent;
   private String loadingProgressText = "Loading";
   private Font yp = new Font("TimesRoman", 0, 15);
   private Font zp = new Font("Helvetica", 1, 13);
   private Font aq = new Font("Helvetica", 0, 12);
   private Image bq;
   private Graphics graphics;
   public boolean keyLcb = false;
   public boolean keyRcb = false;
   public boolean keyLeft = false;
   public boolean keyRight = false;
   public boolean keyUp = false;
   public boolean keyDown = false;
   public boolean keySpace = false;
   public boolean keyNm = false;
   public int threadSleep = 1;
   public int mouseX;
   public int mouseY;
   public int mouseButtonDown;
   public int lastMouseButtonDown;
   public int lastKeyCode1;
   public int lastKeyCode2;
   public boolean interlace = false;
   public String inputTextCurrent = "";
   public String inputTextFinal = "";
   public String inputPMCurrent = "";
   public String inputPMFinal = "";
   public int xq;

   public void startGame() {
   }

   public synchronized void handleInputs() {
   }

   public void onClosing() {
   }

   public synchronized void draw() {
   }

   public void drawHbar() {
   }

   public final void startApplication(int var1, int var2, String var3, boolean var4) {
      this.appletMode = false;
      System.out.println("Started application");
      this.appletWidth = var1;
      this.appletHeight = var2;
      gameFrame = new GameFrame(this, var1, var2, var3, var4, false);
      this.loadingStep = 1;
      this.appletThread = new Thread(this);
      this.appletThread.start();
      this.appletThread.setPriority(1);
   }

   public final boolean inAppletMode() {
      return this.appletMode || true;
   }

   public final void createFrame(int var1, int var2, String var3, boolean var4) {
      if(gameFrame == null) {
         this.appletWidth = var1;
         this.appletHeight = var2;
         gameFrame = new GameFrame(this, var1, var2, var3, var4, this.appletMode);
      }
   }

   public final void resizeFrame(int var1, int var2) {
      if(gameFrame != null) {
         gameFrame.resize(var1, var2);
         this.appletWidth = var1;
         this.appletHeight = var2;
      }
   }

   public final void setFrameIcon(Image var1) {
      if(gameFrame != null) {
         gameFrame.setIconImage(var1);
      }
   }

   public void resize(int var1, int var2) {
      this.resizeFrame(var1, var2);
   }

   public final Graphics getGraphics() {
      return gameFrame == null?super.getGraphics(): gameFrame.getGraphics();
   }

   public final int getAppletWidth() {
      return this.appletWidth;
   }

   public final int getAppletHeight() {
      return this.appletHeight;
   }

   public final Image createImage(int var1, int var2) {
      return gameFrame == null?super.createImage(var1, var2): gameFrame.createImage(var1, var2);
   }

   public Frame getGameFrame() {
      return gameFrame;
   }

   public final void setTargetFPS(int var1) {
      this.targetFPS = 1000 / var1;
   }

   public final void setMaxDrawTime(int var1) {
      this.maxDrawTime = var1;
   }

   public final void resetTimings() {
      for(int var1 = 0; var1 < 10; ++var1) {
         this.timings[var1] = 0L;
      }

   }

   public synchronized boolean keyDown(Event var1, int key) {
	  this.handleKeyPress(key);
      this.lastKeyCode1 = key;
      this.lastKeyCode2 = key;
      this.lastMouseAction = 0;
      if(key == 1006) {
         this.keyLeft = true;
      }

      if(key == 1007) {
         this.keyRight = true;
      }

      if(key == 1004) {
         this.keyUp = true;
      }

      if(key == 1005) {
         this.keyDown = true;
      }

      if((char)key == 32) {
         this.keySpace = true;
      }

      if((char)key == 110 || (char)key == 109) {
         this.keyNm = true;
      }

      if((char)key == 78 || (char)key == 77) {
         this.keyNm = true;
      }

      if((char)key == 123) {
         this.keyLcb = true;
      }

      if((char)key == 125) {
         this.keyRcb = true;
      }

      if((char)key == 1008) {
         this.interlace = !this.interlace;
      }

      if((key >= 97 && key <= 122 || key >= 65 && key <= 90 || key >= 48 && key <= 57 || key == 32) && this.inputTextCurrent.length() < 16) {
         this.inputTextCurrent = this.inputTextCurrent + (char)key;
      }

      if(key >= 32 && key <= 122 && this.inputPMCurrent.length() < 80) {
         this.inputPMCurrent = this.inputPMCurrent + (char)key;
      }

      if(key == 8 && this.inputTextCurrent.length() > 0) {
         this.inputTextCurrent = this.inputTextCurrent.substring(0, this.inputTextCurrent.length() - 1);
      }

      if(key == 8 && this.inputPMCurrent.length() > 0) {
         this.inputPMCurrent = this.inputPMCurrent.substring(0, this.inputPMCurrent.length() - 1);
      }

      if(key == 10 || key == 13) {
         this.inputTextFinal = this.inputTextCurrent;
         this.inputPMFinal = this.inputPMCurrent;
      }

      return true;
   }

   public void handleKeyPress(int var1) {
   }

   public synchronized boolean keyUp(Event var1, int var2) {
	  this.lastKeyCode1 = 0;
      if(var2 == 1006) {
         this.keyLeft = false;
      }

      if(var2 == 1007) {
         this.keyRight = false;
      }

      if(var2 == 1004) {
         this.keyUp = false;
      }

      if(var2 == 1005) {
         this.keyDown = false;
      }

      if((char)var2 == 32) {
         this.keySpace = false;
      }

      if((char)var2 == 110 || (char)var2 == 109) {
         this.keyNm = false;
      }

      if((char)var2 == 78 || (char)var2 == 77) {
         this.keyNm = false;
      }

      if((char)var2 == 123) {
         this.keyLcb = false;
      }

      if((char)var2 == 125) {
         this.keyRcb = false;
      }

      return true;
   }

   public synchronized boolean mouseMove(Event var1, int var2, int var3) {
      this.mouseX = var2;
      this.mouseY = var3 + this.rp;
      this.mouseButtonDown = 0;
      this.lastMouseAction = 0;
      return true;
   }

   public synchronized boolean mouseUp(Event var1, int var2, int var3) {
      this.mouseX = var2;
      this.mouseY = var3 + this.rp;
      this.mouseButtonDown = 0;
      return true;
   }

   public synchronized boolean mouseDown(Event var1, int var2, int var3) {
      this.mouseX = var2;
      this.mouseY = var3 + this.rp;
      if(var1.metaDown()) {
         this.mouseButtonDown = 2;
      } else {
         this.mouseButtonDown = 1;
      }

      this.lastMouseButtonDown = this.mouseButtonDown;
      this.lastMouseAction = 0;
      return true;
   }

   public synchronized boolean mouseDrag(Event var1, int var2, int var3) {
      this.mouseX = var2;
      this.mouseY = var3 + this.rp;
      if(var1.metaDown()) {
         this.mouseButtonDown = 2;
      } else {
         this.mouseButtonDown = 1;
      }

      return true;
   }

   public final void init() {
      this.appletMode = true;
      System.out.println("Started applet");
      this.appletWidth = this.size().width;
      this.appletHeight = this.size().height;
      this.loadingStep = 1;
      Utility.appletCodeBase = this.getCodeBase();
      this.appletThread = new Thread(this);
      this.appletThread.start();
   }

   public final void start() {
      if(this.stopTimeout >= 0) {
         this.stopTimeout = 0;
      }

   }

   public final void stop() {
      if(this.stopTimeout >= 0) {
         this.stopTimeout = 4000 / this.targetFPS;
      }

   }

   public final void destroy() {
      this.stopTimeout = -1;

      try {
         Thread.sleep(5000L);
      } catch (Exception var1) {
         ;
      }

      if(this.stopTimeout == -1) {
         System.out.println("5 seconds expired, forcing kill");
         this.closeProgram();
         if(this.appletThread != null) {
            this.appletThread.stop();
            this.appletThread = null;
         }
      }

   }

   public final void closeProgram() {
      this.stopTimeout = -2;
      System.out.println("Closing program");
      this.onClosing();

      try {
         Thread.sleep(1000L);
      } catch (Exception var1) {
         ;
      }

      if(gameFrame != null) {
         gameFrame.dispose();
      }

      if(!this.appletMode) {
         System.exit(0);
      }

   }

   public final void run() {
      if(this.loadingStep == 1) {
         this.loadingStep = 2;
         this.graphics = this.getGraphics();
         this.loadJagex();
         this.drawLoadingScreen(0, "Loading...");
         this.startGame();
         this.loadingStep = 0;
      }

      int var3 = 0;
      int var4 = 256;
      int var5 = 1;
      int var6 = 0;

      for(int var7 = 0; var7 < 10; ++var7) {
         this.timings[var7] = System.currentTimeMillis();
      }

      long var1 = System.currentTimeMillis();

      while(true) {
         do {
            do {
               if(this.stopTimeout < 0) {
                  if(this.stopTimeout == -1) {
                     this.closeProgram();
                  }

                  this.appletThread = null;
                  return;
               }

               if(this.stopTimeout > 0) {
                  --this.stopTimeout;
                  if(this.stopTimeout == 0) {
                     this.closeProgram();
                     this.appletThread = null;
                     return;
                  }
               }

               int var8 = var4;
               int var9 = var5;
               var4 = 300;
               var5 = 1;
               var1 = System.currentTimeMillis();
               if(this.timings[var3] == 0L) {
                  var4 = var8;
                  var5 = var9;
               } else if(var1 > this.timings[var3]) {
                  var4 = (int)((long)(2560 * this.targetFPS) / (var1 - this.timings[var3]));
               }

               if(var4 < 25) {
                  var4 = 25;
               }

               if(var4 > 256) {
                  var4 = 256;
                  var5 = (int)((long)this.targetFPS - (var1 - this.timings[var3]) / 10L);
                  if(var5 < this.threadSleep) {
                     var5 = this.threadSleep;
                  }
               }

               try {
                  Thread.sleep((long)var5);
               } catch (InterruptedException var11) {
                  ;
               }

               this.timings[var3] = var1;
               var3 = (var3 + 1) % 10;
               int var10;
               if(var5 > 1) {
                  for(var10 = 0; var10 < 10; ++var10) {
                     if(this.timings[var10] != 0L) {
                        this.timings[var10] += (long)var5;
                     }
                  }
               }

               var10 = 0;

               while(var6 < 256) {
                  this.handleInputs();
                  var6 += var4;
                  ++var10;
                  if(var10 > this.maxDrawTime) {
                     var6 = 0;
                     this.interlaceTimer += 6;
                     if(this.interlaceTimer > 25) {
                        this.interlaceTimer = 0;
                        this.interlace = true;
                     }
                     break;
                  }
               }

               --this.interlaceTimer;
               var6 &= 255;
               this.draw();
               this.xq = 1000 * var4 / (this.targetFPS * 256);
               /*if(this.op && var3 == 0) {
                  this.showStatus("Fps:" + this.xq + "Del:" + var5);
               }*/
            } while(gameFrame == null);
         } while(gameFrame.getFrameWidth() == this.appletWidth && gameFrame.getFrameHeight() == this.appletHeight);

         this.resize(gameFrame.getFrameWidth(), gameFrame.getFrameHeight());
      }
   }

   public final void update(Graphics var1) {
      this.paint(var1);
   }

   public final void paint(Graphics var1) {
      if(this.loadingStep == 2 && this.bq != null) {
         this.drawLoadingScreen(this.loadingProgressPercent, this.loadingProgressText);
      } else {
         if(this.loadingStep == 0) {
            this.drawHbar();
         }

      }
   }

   public void loadJagex() {
      try {
         byte[] var1 = Utility.readDataFile("jagex.jag");
         byte[] var2 = Utility.unpackData("logo.tga", 0, var1);
         this.bq = this.createImage(var2);
         Surface.addFont(Utility.unpackData("h11p.jf", 0, var1));
         Surface.addFont(Utility.unpackData("h12b.jf", 0, var1));
         Surface.addFont(Utility.unpackData("h12p.jf", 0, var1));
         Surface.addFont(Utility.unpackData("h13b.jf", 0, var1));
         Surface.addFont(Utility.unpackData("h14b.jf", 0, var1));
         Surface.addFont(Utility.unpackData("h16b.jf", 0, var1));
         Surface.addFont(Utility.unpackData("h20b.jf", 0, var1));
         Surface.addFont(Utility.unpackData("h24b.jf", 0, var1));
      } catch (IOException var3) {
         System.out.println("Error loading jagex.dat");
      }
   }

   public void drawLoadingScreen(int var1, String var2) {
      int var3 = (this.appletWidth - 281) / 2;
      int var4 = (this.appletHeight - 148) / 2;
      this.graphics.setColor(Color.black);
      this.graphics.fillRect(0, 0, this.appletWidth, this.appletHeight);
      if(!this.vp) {
         this.graphics.drawImage(this.bq, var3, var4, this);
      }

      var3 += 2;
      var4 += 90;
      this.loadingProgressPercent = var1;
      this.loadingProgressText = var2;
      this.graphics.setColor(new Color(132, 132, 132));
      if(this.vp) {
         this.graphics.setColor(new Color(220, 0, 0));
      }

      this.graphics.drawRect(var3 - 2, var4 - 2, 280, 23);
      this.graphics.fillRect(var3, var4, 277 * var1 / 100, 20);
      this.graphics.setColor(new Color(198, 198, 198));
      if(this.vp) {
         this.graphics.setColor(new Color(255, 255, 255));
      }

      this.drawString(this.graphics, var2, this.yp, var3 + 138, var4 + 10);
      if(!this.vp) {
         this.drawString(this.graphics, "Created by JAGeX - visit www.jagex.com", this.zp, var3 + 138, var4 + 30);
         this.drawString(this.graphics, "Copyright \u00a92000 Andrew Gower", this.zp, var3 + 138, var4 + 44);
      } else {
         this.graphics.setColor(new Color(132, 132, 152));
         this.drawString(this.graphics, "Copyright \u00a92000 Andrew Gower", this.aq, var3 + 138, this.appletHeight - 20);
      }

      if(this.up != null) {
         this.graphics.setColor(Color.white);
         this.drawString(this.graphics, this.up, this.zp, var3 + 138, var4 - 120);
      }

   }

   public void showLoadingProgress(int var1, String var2) {
      int var3 = (this.appletWidth - 281) / 2;
      int var4 = (this.appletHeight - 148) / 2;
      var3 += 2;
      var4 += 90;
      this.loadingProgressPercent = var1;
      this.loadingProgressText = var2;
      int var5 = 277 * var1 / 100;
      this.graphics.setColor(new Color(132, 132, 132));
      if(this.vp) {
         this.graphics.setColor(new Color(220, 0, 0));
      }

      this.graphics.fillRect(var3, var4, var5, 20);
      this.graphics.setColor(Color.black);
      this.graphics.fillRect(var3 + var5, var4, 277 - var5, 20);
      this.graphics.setColor(new Color(198, 198, 198));
      if(this.vp) {
         this.graphics.setColor(new Color(255, 255, 255));
      }

      this.drawString(this.graphics, var2, this.yp, var3 + 138, var4 + 10);
   }

   public void drawString(Graphics g, String var2, Font var3, int var4, int var5) {
      Object var6;
      if(gameFrame == null) {
         var6 = this;
      } else {
         var6 = gameFrame;
      }

      FontMetrics var7 = ((Component)var6).getFontMetrics(var3);
      var7.stringWidth(var2);
      g.setFont(var3);
      g.drawString(var2, var4 - var7.stringWidth(var2) / 2, var5 + var7.getHeight() / 4);
   }

   public Image createImage(byte[] var1) {
      int var2 = var1[13] * 256 + var1[12];
      int var3 = var1[15] * 256 + var1[14];
      byte[] var4 = new byte[256];
      byte[] var5 = new byte[256];
      byte[] var6 = new byte[256];

      for(int var7 = 0; var7 < 256; ++var7) {
         var4[var7] = var1[20 + var7 * 3];
         var5[var7] = var1[19 + var7 * 3];
         var6[var7] = var1[18 + var7 * 3];
      }

      IndexColorModel var8 = new IndexColorModel(8, 256, var4, var5, var6);
      byte[] var9 = new byte[var2 * var3];
      int var10 = 0;

      for(int var11 = var3 - 1; var11 >= 0; --var11) {
         for(int var12 = 0; var12 < var2; ++var12) {
            var9[var10++] = var1[786 + var12 + var11 * var2];
         }
      }

      MemoryImageSource var14 = new MemoryImageSource(var2, var3, var8, var9, 0, var2);
      Image var13 = this.createImage(var14);
      return var13;
   }

   public byte[] readDataFile(String var1, String var2, int var3) throws IOException {
	   if(true) {
	         var1 = "cache/" + var1;
	      }
	   
	   int var4 = 0;
      int var5 = 0;
      int var6 = 0;
      byte[] var7 = null;

      while(var4 < 2) {
         try {
            this.showLoadingProgress(var3, "Loading " + var2 + " - 0%");
            if(var4 == 1) {
               var1 = var1.toUpperCase();
            }

            InputStream var8 = Utility.openStream(var1);
            DataInputStream var9 = new DataInputStream(var8);
            byte[] var10 = new byte[6];
            var9.readFully(var10, 0, 6);
            var5 = ((var10[0] & 255) << 16) + ((var10[1] & 255) << 8) + (var10[2] & 255);
            var6 = ((var10[3] & 255) << 16) + ((var10[4] & 255) << 8) + (var10[5] & 255);
            this.showLoadingProgress(var3, "Loading " + var2 + " - 5%");
            int var11 = 0;
            var7 = new byte[var6];

            while(var11 < var6) {
               int var12 = var6 - var11;
               if(var12 > 1000) {
                  var12 = 1000;
               }

               var9.readFully(var7, var11, var12);
               var11 += var12;
               this.showLoadingProgress(var3, "Loading " + var2 + " - " + (5 + var11 * 95 / var6) + "%");
            }

            var4 = 2;
            var9.close();
         } catch (IOException var13) {
            ++var4;
         }
      }

      this.showLoadingProgress(var3, "Unpacking " + var2);
      if(var6 != var5) {
         byte[] var14 = new byte[var5];
         BZLib.decompress(var14, var5, var7, var6, 0);
         return var14;
      } else {
         return var7;
      }
   }
}
