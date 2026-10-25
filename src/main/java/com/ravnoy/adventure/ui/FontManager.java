package com.ravnoy.adventure.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

/**
 * Genererer høyoppløselige, skarpe skrifttyper med full støtte for norske tegn (æ, ø, å).
 */
public class FontManager {
    public static final String NORWEGIAN_CHARS =
        "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ" +
        "0123456789.,:;!?\"'()[]{}/*+-=_<>#%&" +
        "æøåÆØÅéèêÉÈÊáàÁÀöäÖÄüÜ";

    private BitmapFont smallFont;
    private BitmapFont mediumFont;
    private BitmapFont largeFont;
    private BitmapFont titleFont;

    public void init() {
        if (Gdx.files.internal("fonts/font.ttf").exists()) {
            FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/font.ttf"));

            FreeTypeFontGenerator.FreeTypeFontParameter paramSmall = new FreeTypeFontGenerator.FreeTypeFontParameter();
            paramSmall.size = 15;
            paramSmall.characters = NORWEGIAN_CHARS;
            paramSmall.color = Color.WHITE;
            smallFont = generator.generateFont(paramSmall);

            FreeTypeFontGenerator.FreeTypeFontParameter paramMedium = new FreeTypeFontGenerator.FreeTypeFontParameter();
            paramMedium.size = 20;
            paramMedium.characters = NORWEGIAN_CHARS;
            paramMedium.color = Color.WHITE;
            mediumFont = generator.generateFont(paramMedium);

            FreeTypeFontGenerator.FreeTypeFontParameter paramLarge = new FreeTypeFontGenerator.FreeTypeFontParameter();
            paramLarge.size = 26;
            paramLarge.characters = NORWEGIAN_CHARS;
            paramLarge.color = Color.WHITE;
            largeFont = generator.generateFont(paramLarge);

            FreeTypeFontGenerator.FreeTypeFontParameter paramTitle = new FreeTypeFontGenerator.FreeTypeFontParameter();
            paramTitle.size = 36;
            paramTitle.characters = NORWEGIAN_CHARS;
            paramTitle.color = new Color(0.95f, 0.85f, 0.45f, 1f);
            titleFont = generator.generateFont(paramTitle);

            generator.dispose();
        } else {
            // Fallback til standard BitmapFont hvis font.ttf ikke finnes
            smallFont = new BitmapFont();
            mediumFont = new BitmapFont();
            largeFont = new BitmapFont();
            titleFont = new BitmapFont();
        }
    }

    public BitmapFont getSmallFont() { return smallFont; }
    public BitmapFont getMediumFont() { return mediumFont; }
    public BitmapFont getLargeFont() { return largeFont; }
    public BitmapFont getTitleFont() { return titleFont; }

    public void dispose() {
        if (smallFont != null) smallFont.dispose();
        if (mediumFont != null) mediumFont.dispose();
        if (largeFont != null) largeFont.dispose();
        if (titleFont != null) titleFont.dispose();
    }
}
