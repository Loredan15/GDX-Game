package ru.maxol.utils;

import com.badlogic.gdx.physics.box2d.*;
import ru.maxol.screens.GameScreen;

public class MyContactListener implements ContactListener {
    public static int cnt=0;
    public static boolean isDamage=false;
    @Override
    public void beginContact(Contact contact) {
        Fixture a = contact.getFixtureA();
        Fixture b = contact.getFixtureB();

        if (a.getUserData().equals("Hero") && b.getUserData().equals("ring")) {
            GameScreen.bodyToDelete.add(b.getBody());
        }
        if (b.getUserData().equals("Hero") && a.getUserData().equals("ring")) {
            GameScreen.bodyToDelete.add(a.getBody());
        }

        if (a.getUserData().equals("legs") && b.getUserData().equals("stone")) {
            cnt++;
        }
        if (b.getUserData().equals("legs") && a.getUserData().equals("stone")) {
            cnt++;
        }

        if (a.getUserData().equals("legs") && b.getUserData().equals("damage")) {
            isDamage = true;
        }
        if (b.getUserData().equals("legs") && a.getUserData().equals("damage")) {
            isDamage = true;
        }
    }

    @Override
    public void endContact(Contact contact) {
        Fixture a = contact.getFixtureA();
        Fixture b = contact.getFixtureB();

        if (a.getUserData().equals("legs") && b.getUserData().equals("stone")) {
            cnt--;
        }
        if (b.getUserData().equals("legs") && a.getUserData().equals("stone")) {
            cnt--;
        }
        if (a.getUserData().equals("legs") && b.getUserData().equals("damage")) {
            isDamage = false;
        }
        if (b.getUserData().equals("legs") && a.getUserData().equals("damage")) {
            isDamage = false;
        }
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
