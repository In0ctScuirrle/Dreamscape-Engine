package lu.embellished_duck.dreamscape_engine.output;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.net.URL;

@Slf4j
@Getter
@Setter
public class Audio {

    //=======================
    // INSTANTIATE VARIABLES
    //=======================
    private Clip clip;

    private URL[] soundURLs = new URL[30];


    //=============
    // CONSTRUCTOR
    //=============
    public Audio() {

        //Instantiate like this:

        //soundURLs[0] = getClass().getResource("/assets/sound/music\soundfx/FileName.wav")

    }//End of Constructor


    //======================================
    // METHOD TO SET THE CURRENT SOUND FILE
    //======================================
    public void setFile(int index) {

        try {

            AudioInputStream inputStream = AudioSystem.getAudioInputStream(soundURLs[index]);

            clip = AudioSystem.getClip();
            clip.open(inputStream);

        } catch (Exception e) {

            log.error("""
                    Something went wrong while importing the audio file. Here are some of the suspects:
                    
                    1. The sound directory wasn't specified.
                    
                    2. The audio file is not in the correct format.
                    """);

        }//End of Try-Catch Statement

    }//End of Method


    //========================================================
    // WHENEVER AN AUDIO FILE WILL BE PLAYED CALL THIS METHOD
    //========================================================
    public void play() {

        clip.start();

    }//End of Method


    //===========================================================
    // METHOD FOR LOOPING A TRACK (MOST LIKELY BACKGROUND MUSIC)
    //===========================================================
    public void loop() {

        clip.loop(Clip.LOOP_CONTINUOUSLY);

    }//End of Method


    //=================================================
    // METHOD TO STOP THE AUDIO CURRENTLY BEING PLAYED
    //=================================================
    public void stop() {

        clip.stop();

    }//End of Method

}//End of Class