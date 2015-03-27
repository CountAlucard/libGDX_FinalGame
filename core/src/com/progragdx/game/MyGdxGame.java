package com.progragdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class MyGdxGame extends ApplicationAdapter {


    public OrthographicCamera camera;
    private Music background;
    Texture game_overtxt, youwin, frogger;
    SpriteBatch spriteBatch;
	BitmapFont fuente;
    Frog frog;
    Cocodrile cocodrile;
    Animation animation;
    float time;
    boolean game_over, winner;

    /*
        Metodo invocado en el momento de crearse la aplicacion.
     */
	
	@Override
	public void create () {
        background = Gdx.audio.newMusic(Gdx.files.internal("Frogger music - The Begining.mp3"));
        // start the playback of the background music immediately
        background.setLooping(true);
        background.play();
        frogger = new Texture("logo.png");
        youwin = new Texture("winner.jpg");
        winner = false;

        game_overtxt = new Texture("gover.png");
        game_over = false;

		spriteBatch = new SpriteBatch();
		fuente = new BitmapFont();

        //Crea la camara y define la zona de vision del juego (toda la pantalla)
        camera = new OrthographicCamera(1024, 720);
        camera.setToOrtho(false, 1024, 720);
        camera.update();

        frog = new Frog(10, 10);
        cocodrile = new Cocodrile(0, 300);

	}

	@Override
	public void render () {

        float dt = Gdx.graphics.getDeltaTime();

        animation = new Animation(1f / 3f, new Sprite(new Texture("snake1.png")),new Sprite(new Texture("snake2.png")),new Sprite(new Texture("snake3.png")),new Sprite(new Texture("snake4.png")));
        animation.setPlayMode(Animation.PlayMode.LOOP);

        // Pinta el fondo de azul oscuro.
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);

        // Limpia la pantalla.
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //Actualiza la camara.

        camera.zoom = 1/5f;
        camera.update();

        handleInput(dt);
        update(dt);

		spriteBatch.begin();
        spriteBatch.draw(animation.getKeyFrame(time += dt), 512,100);
        spriteBatch.draw(frogger, 480, 640);
        frog.render(spriteBatch);
        cocodrile.render(spriteBatch);
        if(game_over == true)
            spriteBatch.draw(game_overtxt, 0, 0);
        if(winner == true)
            spriteBatch.draw(youwin, 0,0);
		spriteBatch.end();



        if(frog.getPosition() <= 1) {
            game_over = true;
            background.pause();
        }
        else if(frog.getPosition() >= 1250) {
            game_over = true;
            background.pause();
        }
        else {
            game_over = false;
            background.play();
        }

        if(frog.getPositionY() >= 680)
            winner = true;
	}

    private void handleInput(float dt){
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
            frog.state = Frog.State.LEFT;
            frog.move(new Vector2(-dt, 0));
        }

        else if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
            frog.state = Frog.State.RIGHT;
            frog.move(new Vector2(dt, 0));
        }

        else if(Gdx.input.isKeyPressed(Input.Keys.UP)){
            frog.state = Frog.State.UP;
            frog.move(new Vector2(0, dt));
        }

        else if(Gdx.input.isKeyPressed(Input.Keys.DOWN)){
            frog.state = Frog.State.DOWN;
            frog.move(new Vector2(0, -dt));
        }

        else
            frog.state = Frog.State.IDLE;
    }

    private void update(float dt){

        frog.update(dt);
        cocodrile.update(dt);
    }

    @Override
    public void dispose(){
        spriteBatch.dispose();
    }
}
