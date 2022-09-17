//package ru.maxol;
//
//import com.badlogic.gdx.ApplicationAdapter;
//import com.badlogic.gdx.Gdx;
//import com.badlogic.gdx.Input;
//import com.badlogic.gdx.audio.Music;
//import com.badlogic.gdx.audio.Sound;
//import com.badlogic.gdx.graphics.Color;
//import com.badlogic.gdx.graphics.OrthographicCamera;
//import com.badlogic.gdx.graphics.Texture;
//import com.badlogic.gdx.graphics.g2d.Animation;
//import com.badlogic.gdx.graphics.g2d.SpriteBatch;
//import com.badlogic.gdx.graphics.g2d.TextureRegion;
//import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
//import com.badlogic.gdx.maps.Map;
//import com.badlogic.gdx.maps.MapLayer;
//import com.badlogic.gdx.maps.MapObject;
//import com.badlogic.gdx.maps.objects.RectangleMapObject;
//import com.badlogic.gdx.maps.tiled.TiledMap;
//import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
//import com.badlogic.gdx.maps.tiled.TmxMapLoader;
//import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
//import com.badlogic.gdx.math.MathUtils;
//import com.badlogic.gdx.math.Rectangle;
//import com.badlogic.gdx.math.Vector2;
//import com.badlogic.gdx.physics.box2d.Body;
//import com.badlogic.gdx.physics.box2d.BodyDef;
//import com.badlogic.gdx.physics.box2d.FixtureDef;
//import com.badlogic.gdx.physics.box2d.PolygonShape;
//import com.badlogic.gdx.utils.Array;
//import com.badlogic.gdx.utils.ScreenUtils;
//
//public class MyGdxGame extends ApplicationAdapter {
//	private SpriteBatch batch;
//	private ShapeRenderer shapeRenderer;
//	private Texture img, coinImg;
//	private MyAtlasAnim stand, run, jump, tmpA;
//	private Music music;
//	private Sound sound;
//	private MyInputProcessor myInputProcessor;
//	private OrthographicCamera camera;
//	private float x,y;
//	private int dir = 0, step =1;
//	private Rectangle rectangle, window;
//	private PhysX physX;
//	private Body body;
//	private TiledMap map;
//	private OrthogonalTiledMapRenderer mapRenderer;
//
//
//	@Override
//	public void create () {
//		map = new TmxMapLoader().load("map/безымянный.tmx");
//		mapRenderer = new OrthogonalTiledMapRenderer(map);
//
//		physX = new PhysX();
//
//		BodyDef def = new BodyDef();
//		FixtureDef fdef = new FixtureDef();
//		PolygonShape shape = new PolygonShape();
//
//		def.type = BodyDef.BodyType.StaticBody;
//		fdef.shape = shape;
//		fdef.density = 1;
//		fdef.friction = 0;
//		fdef.restitution = 1;
//
//		MapLayer env = map.getLayers().get("env");
//		Array<RectangleMapObject> rect = env.getObjects().getByType(RectangleMapObject.class);
//		for (int i = 0; i < rect.size; i++) {
//			float x = rect.get(i).getRectangle().x + rect.get(i).getRectangle().width / 2;
//			float y = rect.get(i).getRectangle().y + rect.get(i).getRectangle().height / 2;
//			float w = rect.get(i).getRectangle().width / 2;
//			float h = rect.get(i).getRectangle().height / 2;
//			def.position.set(x, y);
//			shape.setAsBox(w, h);
//			physX.world.createBody(def).createFixture(fdef).setUserData("Kubik");
//		}
//
//		def.type = BodyDef.BodyType.DynamicBody;
//		def.gravityScale = 4;
//		env = map.getLayers().get("dyn");
//		rect = env.getObjects().getByType(RectangleMapObject.class);
//		for (int i = 0; i < rect.size; i++) {
//			float x = rect.get(i).getRectangle().x + rect.get(i).getRectangle().width / 2;
//			float y = rect.get(i).getRectangle().y + rect.get(i).getRectangle().height / 2;
//			float w = rect.get(i).getRectangle().width / 2;
//			float h = rect.get(i).getRectangle().height / 2;
//			def.position.set(x, y);
//			shape.setAsBox(w, h);
//
//			fdef.density = (float) rect.get(i).getProperties().get("density");;
//			fdef.friction = 0;
//			fdef.restitution = 1;
//			physX.world.createBody(def).createFixture(fdef).setUserData("Kubik");
//		}
//
//		env = map.getLayers().get("hero");
//		RectangleMapObject hero = (RectangleMapObject) env.getObjects().get("Hero");
//		float x = hero.getRectangle().x + hero.getRectangle().width / 2;
//		float y = hero.getRectangle().y + hero.getRectangle().height / 2;
//		float w = hero.getRectangle().width / 2;
//		float h = hero.getRectangle().height / 2;
//		def.position.set(x, y);
//		shape.setAsBox(w, h);
//		fdef.shape = shape;
//		fdef.density = 1;
//		fdef.friction = 0;
//		fdef.restitution = 1;
//		body = physX.world.createBody(def);
//		body.createFixture(fdef).setUserData("Kubik");
//
//		shape.dispose();
//
//		//shapeRenderer = new ShapeRenderer();
//		rectangle = new Rectangle();
//		window = new Rectangle(0,0,Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
//		myInputProcessor = new MyInputProcessor();
//		Gdx.input.setInputProcessor(myInputProcessor);
//
//		music = Gdx.audio.newMusic(Gdx.files.internal("MC_Hammer_-_U_Cant_Touch_This_b128f0d256.mp3"));
//		music.setVolume(0.025f);
//		music.setLooping(true);
//		music.play();
//
//		sound = Gdx.audio.newSound(Gdx.files.internal("7b999d49fa57974.mp3"));
//
//		batch = new SpriteBatch();
//		img = new Texture("badlogic.jpg");
//		coinImg = new Texture("Full Coinss.png");
//		stand = new MyAtlasAnim("atlas/jamp.atlas", "stand", 17, false, "single_on_dirty_stone_step_flip_flop_007_30443.mp3");
//		jump = new MyAtlasAnim("atlas/jamp.atlas", "jamp", 17, true, "single_on_dirty_stone_step_flip_flop_007_30443.mp3");
//		run = new MyAtlasAnim("atlas/jamp.atlas", "run", 17, true, "single_on_dirty_stone_step_flip_flop_007_30443.mp3");
//		tmpA = stand;
//
//		camera = new OrthographicCamera();
//	}
//
//	@Override
//	public void render () {
//		ScreenUtils.clear(Color.BLACK);
//
//		camera.position.x = body.getPosition().x;
//		camera.position.y = body.getPosition().y;
//		camera.zoom = 0.5f;
//		camera.update();
//
//		mapRenderer.setView(camera);
//		mapRenderer.render();
//
//		tmpA = stand;
//		dir = 0;
//
//		if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {sound.play(1, 1, 0);}
//
//		if (myInputProcessor.getOutString().contains("A")) {
//			dir = -1;
//			tmpA = run;
//			body.applyForceToCenter(new Vector2(-10000, 0f), true);
//		}
//		if (myInputProcessor.getOutString().contains("D")) {
//			dir = 1;
//			tmpA = run;
//			body.applyForceToCenter(new Vector2(10000, 0f), true);
//		}
//		if (myInputProcessor.getOutString().contains("W")) {
//			y++;
//			tmpA = jump;
//		}
//		if (myInputProcessor.getOutString().contains("S")) y--;
//		if (myInputProcessor.getOutString().contains("Space")) {
//			body.applyForceToCenter(new Vector2(0, 100000f), true);
//		}
//
//		if (dir == -1) x-=step;
//		if (dir == 1) x+=step;
//
//		tmpA.setTime(Gdx.graphics.getDeltaTime());
//		TextureRegion tmp = tmpA.draw();
//		if (!tmpA.draw().isFlipX() & dir == -1) tmpA.draw().flip(true, false);
//		if (tmpA.draw().isFlipX() & dir == 1) tmpA.draw().flip(true, false);
//
//		rectangle.x = x;
//		rectangle.y = y;
//		rectangle.width = tmp.getRegionWidth();
//		rectangle.height = tmp.getRegionHeight();
//
//		System.out.println(myInputProcessor.getOutString());
//
//		float x = body.getPosition().x - 2.5f/camera.zoom;
//		float y = body.getPosition().y - 2.5f/camera.zoom;
//
//
//		batch.setProjectionMatrix(camera.combined);
//		batch.begin();
//		batch.draw(tmpA.draw(), x, y);
//		batch.end();
//
//
//		if (!window.contains(rectangle)) Gdx.graphics.setTitle("Out"); else Gdx.graphics.setTitle("In");
//
//		physX.step();
//		physX.debugDraw(camera);
//	}
//
//	@Override
//	public void resize(int width, int height) {
//		camera.viewportHeight = height;
//		camera.viewportWidth = width;
//	}
//
//	@Override
//	public void dispose () {
//		batch.dispose();
//		img.dispose();
//		stand.dispose();
//		run.dispose();
//		music.dispose();
//		sound.dispose();
//		map.dispose();
//		mapRenderer.dispose();
//	}
//}
