package jagex.client;

import java.awt.Event;
import java.awt.Frame;
import java.awt.Graphics;

public class GameFrame extends Frame {
   int frameWidth;
   int frameHeight;
   int translate;
   int yoff = 28;
   GameApplet gameApplet;
   Graphics graphics;

   public GameFrame(GameApplet var1, int var2, int var3, String var4, boolean var5, boolean var6) {
      this.frameWidth = var2;
      this.frameHeight = var3;
      this.gameApplet = var1;
      if(var6) {
         this.yoff = 48;
      } else {
         this.yoff = 28;
      }

      this.setTitle(var4);
      this.setResizable(var5);
      this.show();
      this.toFront();
      this.resize(this.frameWidth, this.frameHeight);
      this.graphics = this.getGraphics();
   }

   public Graphics getGraphics() {
      Graphics var1 = super.getGraphics();
      if(this.translate == 0) {
         var1.translate(0, 24);
      } else {
         var1.translate(-5, 0);
      }

      return var1;
   }

   public void resize(int var1, int var2) {
      super.resize(var1, var2 + this.yoff);
   }

   public int getFrameWidth() {
      return this.size().width;
   }

   public int getFrameHeight() {
      return this.size().height - this.yoff;
   }

   public boolean handleEvent(Event var1) {
	   if(var1.id == 401) {
         this.gameApplet.keyDown(var1, var1.key);
      } else if(var1.id == 402) {
         this.gameApplet.keyUp(var1, var1.key);
      } else if(var1.id == 501) {
         this.gameApplet.mouseDown(var1, var1.x, var1.y - 24);
      } else if(var1.id == 506) {
         this.gameApplet.mouseDrag(var1, var1.x, var1.y - 24);
      } else if(var1.id == 502) {
         this.gameApplet.mouseUp(var1, var1.x, var1.y - 24);
      } else if(var1.id == 503) {
         this.gameApplet.mouseMove(var1, var1.x, var1.y - 24);
      } else if(var1.id == 201) {
         this.gameApplet.destroy();
      } else if(var1.id == 1001) {
         this.gameApplet.action(var1, var1.target);
      } else if(var1.id == 403) {
         this.gameApplet.keyDown(var1, var1.key);
      } else if(var1.id == 404) {
         this.gameApplet.keyUp(var1, var1.key);
      }

      return true;
   }

   public final void paint(Graphics var1) {
      this.gameApplet.paint(var1);
   }
}
