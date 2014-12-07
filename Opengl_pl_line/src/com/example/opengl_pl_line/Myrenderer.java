package com.example.opengl_pl_line;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView.Renderer;
import android.util.Log;

public class Myrenderer implements Renderer {
 Lines line, southHorz;
	@Override
	public void onDrawFrame(GL10 arg0) {
		// TODO Auto-generated method stub
		GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
  
      southHorz.draw(-0.7f, -0.7f, 0.0f, 0f);
		
	//line.draw(-0.5f, 0.5f, -0.5f, -0.5f);
		
		
	}

	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		// TODO Auto-generated method stub
		  Log.i("JO", "onSurfaceChanged");
		    // Adjust the viewport based on geometry changes,
		    // such as screen rotation
		    GLES20.glViewport(0, 0, width, height);

	}

	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		// TODO Auto-generated method stub
		GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
	    line= new Lines();
	    southHorz= new Lines();
	}
	public static int loadShader(int type, String shaderCode) {

	    // create a vertex shader type (GLES20.GL_VERTEX_SHADER)
	    // or a fragment shader type (GLES20.GL_FRAGMENT_SHADER)
	    int shader = GLES20.glCreateShader(type);

	    // add the source code to the shader and compile it
	    GLES20.glShaderSource(shader, shaderCode);
	    GLES20.glCompileShader(shader);

	    return shader;
	  }

}


class Lines {

final String vertexShaderCode = "attribute vec4 vPosition;"
     + "void main() {" + "  gl_Position = vPosition;" + "}";

final String fragmentShaderCode = "precision mediump float;"
     + "uniform vec4 vColor;" + "void main() {"
     + "  gl_FragColor = vColor;" + "}";

final FloatBuffer vertexBuffer;
final int mProgram;
int mPositionHandle;
int mColorHandle;

//number of coordinates per vertex in this array
final int COORDS_PER_VERTEX = 3;
float lineCoords[] = new float[6];
final int vertexCount = lineCoords.length / COORDS_PER_VERTEX;
final int vertexStride = COORDS_PER_VERTEX * 4; // bytes per vertex
//Set color with red, green, blue and alpha (opacity) values
float color[] = { 0.63671875f, 0.76953125f, 0.22265625f, 1.0f };

public Lines() {

 // initialize vertex byte buffer for shape coordinates
 ByteBuffer bb = ByteBuffer.allocateDirect(
 // (number of coordinate values * 4 bytes per float)
         lineCoords.length * 4);
 // use the device hardware's native byte order
 bb.order(ByteOrder.nativeOrder());

 // create a floating point buffer from the ByteBuffer
 vertexBuffer = bb.asFloatBuffer();

 // prepare shaders and OpenGL program
 int vertexShader = Myrenderer.loadShader(GLES20.GL_VERTEX_SHADER,
         vertexShaderCode);
 int fragmentShader = Myrenderer.loadShader(GLES20.GL_FRAGMENT_SHADER,
         fragmentShaderCode);

 mProgram = GLES20.glCreateProgram(); // create empty OpenGL Program
 GLES20.glAttachShader(mProgram, vertexShader); // add the vertex shader
                                                 // to program
 GLES20.glAttachShader(mProgram, fragmentShader); // add the fragment
                                                     // shader to program
 GLES20.glLinkProgram(mProgram); // create OpenGL program executables
}

public void draw(float dX,float dY,float uX,float uY) {

 lineCoords[0] = dX;
 lineCoords[1] = dY;
 lineCoords[2] = 0.0f;
 lineCoords[3] = uX;
 lineCoords[4] = uY;
 lineCoords[5] = 0.0f;


 vertexBuffer.put(lineCoords);
 vertexBuffer.position(0);
 // Add program to OpenGL environment
 GLES20.glUseProgram(mProgram);

 // get handle to vertex shader's vPosition member
 mPositionHandle = GLES20.glGetAttribLocation(mProgram, "vPosition");

 // Enable a handle to the triangle vertices
 GLES20.glEnableVertexAttribArray(mPositionHandle);

 // Prepare the triangle coordinate data
 GLES20.glVertexAttribPointer(mPositionHandle, COORDS_PER_VERTEX,
         GLES20.GL_FLOAT, false, vertexStride, vertexBuffer);

 // get handle to fragment shader's vColor member
 mColorHandle = GLES20.glGetUniformLocation(mProgram, "vColor");

 // Set color for drawing the triangle
 GLES20.glUniform4fv(mColorHandle, 1, color, 0);

 // Draw the triangle
 GLES20.glDrawArrays(GLES20.GL_LINES, 0, vertexCount);

 // Disable vertex array
 GLES20.glDisableVertexAttribArray(mPositionHandle);
       }

        }




   


	      




