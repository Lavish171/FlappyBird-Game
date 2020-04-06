//this file is before the putting of the game over state ,but it contains the scorre board.
package com.lavishgarg.flappybird;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

import java.util.Random;

import sun.rmi.runtime.Log;

public class FalppyBIrd extends ApplicationAdapter {
	SpriteBatch batch;
	Texture background;
	Texture birds[];
	Texture gameover;
	Texture toptube;
	Texture bottomtube;
	int flapstate=0;
	int speedcontrol=0;
	int birdy=0;
	int gamestate=0;
	float velocity=0;
	float gravity=.8f;
	float gap=400;
	float maxoffset=0;
	int nooftubes=4;
	Random randomgenerator;
	float tubeoffset[]=new float[nooftubes];
    float tubex[]=new float[nooftubes];
    float maxtubeoffset;
    float distancebetweenthetubes;
    float tubevelocity=11;
    Circle birdcircle;
    ShapeRenderer shapeRenderer;
	Rectangle [] toptuberectangles;
	Rectangle [] bottomtuberectangles;
    BitmapFont font;
    private int score;
    private String yourScoreName;
    int scoringtube=0;


	@Override
	public void create () {
		batch = new SpriteBatch();
		background=new Texture("bg.png");
		birds=new Texture[2];
        birdcircle=new Circle();
        gameover=new Texture("gameover.jpg");
        shapeRenderer=new ShapeRenderer();
		birds[0]=new Texture("bird.png");
		birds[1]=new Texture("bird2.png");
		toptube=new Texture("toptube.png");
		bottomtube=new Texture("bottomtube.png");
		birdy=Gdx.graphics.getHeight()/2-birds[flapstate].getHeight()/2;
		maxtubeoffset=Gdx.graphics.getHeight()/2-gap/2-100;
		randomgenerator=new Random();
		distancebetweenthetubes=Gdx.graphics.getWidth()*4/5;
		toptuberectangles=new Rectangle[nooftubes];
		bottomtuberectangles=new Rectangle[nooftubes];
        score = 0;
        yourScoreName = "score: 0";
        font = new BitmapFont();
        font.setColor(Color.BLACK);
        font.getData().setScale(12);

        scoringtube=0;

		for(int i=0;i<nooftubes;i++)
        {
            tubeoffset[i]=(randomgenerator.nextFloat() -0.5f)*(Gdx.graphics.getHeight()-gap-200);
            tubex[i]=Gdx.graphics.getWidth()/2-toptube.getWidth()/2 +Gdx.graphics.getWidth()*3/4+i*distancebetweenthetubes;
            toptuberectangles[i]=new Rectangle();
            bottomtuberectangles[i]=new Rectangle();
        }

	}
	@Override
	public void render () {
		batch.begin();
        batch.draw(background,0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());

		if(gamestate==1)
		{
		    if(tubex[scoringtube]<Gdx.graphics.getWidth()/2)
            {
                score++;
                Gdx.app.log("Score :",Integer.toString(score));
                if(scoringtube<nooftubes-1)
                    scoringtube++;
                else{
                    scoringtube=0;
                }
            }//checking the position of the scoring tube
            if(Gdx.input.justTouched())  //would execute only when the game state is 1
            {
                velocity-=12;
                Gdx.app.log("HI","Bye");
                gamestate=1;

            }

		//most important code we need to put here.
            for(int i=0;i<nooftubes;i++) {
                if(tubex[i]<-toptube.getWidth()) {
                    tubex[i] += nooftubes * distancebetweenthetubes;
                    tubeoffset[i] = (randomgenerator.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - gap - 200);
                }
                else {
                    tubex[i] -= tubevelocity;
                }
                batch.draw(toptube, tubex[i], Gdx.graphics.getHeight() / 2 + gap / 2 + tubeoffset[i]);
                batch.draw(bottomtube, tubex[i], Gdx.graphics.getHeight() / 2 - gap / 2 - bottomtube.getHeight() + tubeoffset[i]);
                toptuberectangles[i]=new Rectangle(tubex[i],Gdx.graphics.getHeight() / 2 + gap / 2 + tubeoffset[i],toptube.getWidth(),toptube.getHeight());
                bottomtuberectangles[i]=new Rectangle(tubex[i],Gdx.graphics.getHeight() / 2 - gap / 2 - bottomtube.getHeight() +
						tubeoffset[i],bottomtube.getWidth(),bottomtube.getHeight());
            }

            if(birdy>0) {
                velocity += gravity;
                birdy-=velocity;

            }else
            {
                birdy=0;
               // gamestate=2;
            }

		}
		else
		{
			if(Gdx.input.justTouched())
			gamestate=1;
		}

		if (speedcontrol == 6) {
			if (flapstate == 0)
				flapstate = 1;
			else {
				flapstate = 0;
			}
			speedcontrol = 0;
		} else {
			speedcontrol++;
		}
		batch.draw(birds[flapstate],Gdx.graphics.getWidth()/2-birds[flapstate].getWidth()/2,birdy);
		font.draw(batch,String.valueOf(score),960,1750);
		batch.end();

		//code for the shape around the bird
      //  shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
       // shapeRenderer.setColor(Color.RED);
        birdcircle.set(Gdx.graphics.getWidth()/2,birdy+birds[flapstate].getHeight()/2,birds[flapstate].getWidth()/2);
       // shapeRenderer.circle(birdcircle.x,birdcircle.y,birdcircle.radius);
        for(int i=0;i<nooftubes;i++)
		{
		//	shapeRenderer.rect(tubex[i],Gdx.graphics.getHeight() / 2 + gap / 2 + tubeoffset[i],toptube.getWidth(),toptube.getHeight());
		//	shapeRenderer.rect(tubex[i],Gdx.graphics.getHeight() / 2 - gap / 2 - bottomtube.getHeight() +tubeoffset[i],bottomtube.getWidth(),bottomtube.getHeight());

			//now the code would be for the intersector which is been used for the intersection of the anything such as rectangle  to circle
            //or the circle to circle.
            if(Intersector.overlaps(birdcircle,toptuberectangles[i])|| Intersector.overlaps(birdcircle,bottomtuberectangles[i]))
            {
                Gdx.app.log("The Game over","Bye");
               /* score++;
                yourScoreName = "score: " + score;
                batch.begin();
                yourBitmapFontName.setColor(1.0f, 1.0f, 1.0f, 1.0f);
                yourBitmapFontName.draw(batch, yourScoreName, 25, 100);
                batch.end();*/
              // gamestate=2;
            }
		}
       // shapeRenderer.end();

	}
	

}
