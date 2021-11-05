import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Korobeiniki
{
void pentrisMusic(String location){
try
{
    File music = new File (location);
    AudioInputStream audioInput = AudioSystem.getAudioInputStream(music);
    Clip Play = AudioSystem.getClip();
    Play.open(audioInput);
    Play.start();
    Play.loop(Clip.LOOP_CONTINUOUSLY);
}
catch (Exception e)
{}

}
}