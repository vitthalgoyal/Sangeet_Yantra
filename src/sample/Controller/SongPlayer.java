package sample.Controller;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.Parent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.*;
import javafx.stage.FileChooser;
import javafx.util.Duration;
import sample.ClientMain;
import sample.SceneChanger;
import sample.Song;
import sample.SongInfo;


import javax.swing.*;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;
import java.util.ResourceBundle;

public class SongPlayer implements Initializable {

    public Label songName;
    public Slider slider;
    public Button playbtn;
    public Button nextbtn;
    public Button prevbtn;
    public MediaView mediaview;
    public MediaPlayer player;
    public ComboBox Listi;
    public MenuItem playlistMenu;
    public MenuItem albumMenu;
    public MenuItem songMenu;
    public Button SpeedInc;
    public Button SpeedDec;
    public Button Repeat;
    public Slider volumeSlider;
    public Button Shuffle;
    public Button allSongbtn;
    public TextArea subtitleArea;
    public ComboBox currentQueueCB;
    public Button getCurrentPostitionBT;
    public TextField newPostion;
    public Button editPostionBT;
    public TextField currentPositionTF;
    public ComboBox selectSongs2CB;
    public Button addSongBT;
    public ComboBox deleteSongsCB;
    public Slider audio1,audio2,audio3,audio4,audio5;
    public Slider balance;
    List<String> allsongs;
    boolean test=false;
    Song song =new Song();
    List<String> list1=null;
    public AudioSpectrumListener spectrumListener;
    

    public void PlaySong(ActionEvent actionEvent) {

        MediaPlayer.Status status=player.getStatus();
        if(status== MediaPlayer.Status.PLAYING)
        {
            player.pause();
            playbtn.setText("Play");
        }
        else
        {
            player.play();
            playbtn.setText("Pause");
        }




    }

    public void Openlist(ActionEvent actionEvent) {
//        System.out.println("Clicked");

        if (!Listi.getSelectionModel().isEmpty()) {
            slider.setValue(0);

            Boolean check = ClientMain.client.playSong(Listi.getSelectionModel().getSelectedItem().toString());
            //System.out.println(Listi.getSelectionModel().getSelectedItem().toString());
            if (check) {
//                System.out.println("Executed");
                try {
                    Media m = new Media(ClientMain.client.songInfo.file.toURI().toURL().toString());
                    songName.setText(Listi.getSelectionModel().getSelectedItem().toString());






                    // subtitle.getTrackID();
                    if (player != null) {
                        player.dispose();
                    }


                    player = new MediaPlayer(m);
                    AudioEqualizer ae=player.getAudioEqualizer();
                    ObservableList<EqualizerBand> audiolist=ae.getBands();
//                    System.out.println(audiolist.size());
//                    System.out.println(audiolist);
                    /**
                     * Equaliser
                     */
                    {
                        audio1.setMin(EqualizerBand.MIN_GAIN);
                        audio1.setMax(EqualizerBand.MAX_GAIN);
                        audio1.setValue(0);
                        audio2.setMin(EqualizerBand.MIN_GAIN);
                        audio2.setMax(EqualizerBand.MAX_GAIN);
                        audio2.setValue(0);
                        audio3.setMin(EqualizerBand.MIN_GAIN);
                        audio3.setMax(EqualizerBand.MAX_GAIN);
                        audio3.setValue(0);
                        audio4.setMin(EqualizerBand.MIN_GAIN);
                        audio4.setMax(EqualizerBand.MAX_GAIN);
                        audio4.setValue(0);
                        audio5.setMin(EqualizerBand.MIN_GAIN);
                        audio5.setMax(EqualizerBand.MAX_GAIN);
                        audio5.setValue(0);
                        audio1.valueProperty().addListener(new ChangeListener<Number>() {
                        @Override public void changed(ObservableValue<? extends Number> arg0, Number oldValue, Number newValue) {
                            if (player != null) {
                                player.getAudioEqualizer().getBands().get(1).setGain(newValue.doubleValue());
                            }
                        }
                    });
                        audio2.valueProperty().addListener(new ChangeListener<Number>() {
                            @Override public void changed(ObservableValue<? extends Number> arg0, Number oldValue, Number newValue) {
                                if (player != null) {
                                    player.getAudioEqualizer().getBands().get(2).setGain(newValue.doubleValue());
                                }
                            }
                        });
                        audio3.valueProperty().addListener(new ChangeListener<Number>() {
                            @Override public void changed(ObservableValue<? extends Number> arg0, Number oldValue, Number newValue) {
                                if (player != null) {
                                    player.getAudioEqualizer().getBands().get(3).setGain(newValue.doubleValue());
                                }
                            }
                        });
                        audio4.valueProperty().addListener(new ChangeListener<Number>() {
                        @Override public void changed(ObservableValue<? extends Number> arg0, Number oldValue, Number newValue) {
                            if (player != null) {
                                player.getAudioEqualizer().getBands().get(4).setGain(newValue.doubleValue());
                            }
                        }
                    });
                        audio5.valueProperty().addListener(new ChangeListener<Number>() {
                            @Override public void changed(ObservableValue<? extends Number> arg0, Number oldValue, Number newValue) {
                                if (player != null) {
                                    player.getAudioEqualizer().getBands().get(5).setGain(newValue.doubleValue());
                                }
                            }
                        });
                        balance.setMin(-1);
                        balance.setMax(1);
                        balance.setValue(0);
                        balance.valueProperty().addListener(new ChangeListener<Number>() {
                            @Override public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number newValue) {
                                if(player != null) player.setBalance(newValue.doubleValue());
                            }
                        });



                    }

                    mediaview.setMediaPlayer(player);




                    player.currentTimeProperty().addListener(new ChangeListener<Duration>() {
                        @Override
                        public void changed(ObservableValue<? extends Duration> observable, Duration oldValue, Duration newValue) {
                            slider.setValue(newValue.toSeconds());
                        }
                    });


                    slider.setOnMousePressed(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            player.seek(Duration.seconds(slider.getValue()));
                        }
                    });

                    slider.setOnMouseDragged(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            player.seek(Duration.seconds(slider.getValue()));
                        }
                    });

                    player.setOnReady(new Runnable() {
                        @Override
                        public void run() {
                            Duration total = m.getDuration();
                            slider.setMax(total.toSeconds());
                        }
                    });
                    player.setOnEndOfMedia(new Runnable() {
                        @Override
                        public void run() {
                            if (!test) {
                                Listi.getSelectionModel().selectNext();
                                PlayNext(actionEvent);
                            }
                            {
                                Openlist(actionEvent);
                                PlaySong(actionEvent);
                            }
                        }
                    });



                    volumeSlider.setValue(player.getVolume() * 100);
                    volumeSlider.valueProperty().addListener(new InvalidationListener() {
                        @Override
                        public void invalidated(Observable observable) {
                            player.setVolume(volumeSlider.getValue() / 100);
                        }
                    });
                    player.pause();
                    playbtn.setText("Play");


                    System.out.println("song started");
                    setSubtitleArea();

                    //song.startSong();
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("Error in Song");
                }


            } else {
                System.out.println("UI failed");
            }


//        Listi.getChildren().addAll(list);
//        Listi.setMaxHeight(200);
//        Listi.getItems().addAll(data);
//        Listi.setVisible(true);
//        list.setItems(data);


        }
    }
    void setSubtitleArea()
    {      subtitleArea.clear();
        File subtitilefile =ClientMain.client.songInfo.subtitlefile;
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(subtitilefile), "UTF-8"));
            String line;
            while ((line = reader.readLine()) != null)
            {   if(line.length()>0)
            {if((line.charAt(0)>='A'&&line.charAt(0)<='Z')||(line.charAt(0)>='a'&&line.charAt(0)<='z'))
                subtitleArea.appendText(line+"\n");}
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
//        ListView<String> list = new ListView<String>();


        list1=ClientMain.client.getSongs(ClientMain.client.clientInfo.user_id);
        currentQueueCB.getItems().addAll(list1);
        allsongs = ClientMain.client.getAllSongs();
        allsongs.removeAll(list1);
        selectSongs2CB.getItems().addAll(allsongs);
        deleteSongsCB.getItems().addAll(list1);
        //selectSongs2CB.getItems().addAll(list1);

//        DoubleProperty widthProp = mediaview.fitWidthProperty();
//        DoubleProperty heightProp = mediaview.fitHeightProperty();
//
//        widthProp.bind(Bindings.selectDouble(mediaview.sceneProperty(), "width"));
//        heightProp.bind(Bindings.selectDouble(mediaview.sceneProperty(), "height"));

//        System.out.println("INITIALISED");
        try
        {
//            System.out.println(list1);
            Listi.getItems().addAll(list1);
        }
        catch(Exception e)
        {
            System.out.println("Error in getting song");
        }
    }

    public void PlayNext(ActionEvent actionEvent) {

        Listi.getSelectionModel().selectNext();
        PlaySong(actionEvent);
    }

    public void PlayPrev(ActionEvent actionEvent) {
        Listi.getSelectionModel().selectPrevious();
        PlaySong(actionEvent);
    }

    public void goToPlaylist(ActionEvent actionEvent)  {
        if(player!=null)
            player.dispose();
        new SceneChanger().changeScene2("FXML\\Playlist.fxml","Playlist",songName);
    }

    public void goToAlbum(ActionEvent actionEvent){
        if(player!=null)
            player.dispose();
        new SceneChanger().changeScene2("FXML\\Album.fxml","Album",songName);
    }

    public void goToSong(ActionEvent actionEvent) {
        if(player!=null)
            player.dispose();
        new SceneChanger().changeScene2("FXML\\SongPlayer.fxml","Song",songName);
    }
    public void goToHistory(ActionEvent actionEvent) {
        if(player!=null)
            player.dispose();
        new SceneChanger().changeScene2("FXML\\History.fxml","Song", songName);
    }

    public void Fast(ActionEvent actionEvent) {
        player.setRate(player.getRate()+0.5);
    }

    public void Slow(ActionEvent actionEvent) {
        player.setRate(player.getRate()-0.5);
    }

    public void setRepeat(ActionEvent actionEvent) {
        if(test==false)
        {
            Repeat.setText("Circular");
            test=true;
        }
        else
        {
            Repeat.setText("Repeat");
            test=false;
        }
    }

    public void Qshuffle(ActionEvent actionEvent) {
        Collections.shuffle(list1);
        if(player!=null)
        {
            player.dispose();
        }
        Listi.getItems().clear();
        Listi.getItems().addAll(list1);



    }

    public void opendefaultqueue(ActionEvent actionEvent) {
        ClientMain.client.settoDeafault(ClientMain.client.clientInfo.user_id);
//        List<String> list1=ClientMain.client.getSongs(ClientMain.client.clientInfo.user_id);
//        Listi.getItems().clear();
//        Listi.getItems().addAll(list1);
        if(player!=null)
            player.dispose();
        new SceneChanger().changeScene2("FXML\\SongPlayer.fxml","Song",songName);

    }
    public void goToGroup(ActionEvent actionEvent) {
        if(player!=null)
            player.dispose();
        new SceneChanger().changeScene2("FXML\\Group.fxml","Group",songName);
    }

    public void getCurrentPostion(ActionEvent actionEvent) {
        int songposition = currentQueueCB.getSelectionModel().getSelectedIndex();
        currentPositionTF.setText(""+(songposition+1)+"/"+list1.size());

    }

    public void editPosition(ActionEvent actionEvent) {
        List<String > newQueue = list1;
        int newposition = Integer.parseInt(newPostion.getText());
        newposition--;
        int currentPosition = currentQueueCB.getSelectionModel().getSelectedIndex();
        if(newposition>currentPosition)
        {
            String temp = currentQueueCB.getSelectionModel().getSelectedItem().toString();
            for(int i=currentPosition;i<newposition;i++)
            {
                newQueue.set(i,newQueue.get(i+1));

            }
            newQueue.set(newposition,temp);

        }
        else {
            String temp = currentQueueCB.getSelectionModel().getSelectedItem().toString();
            for(int i=currentPosition;i>newposition;i--)
            {
                newQueue.set(i,newQueue.get(i-1));

            }
            newQueue.set(newposition,temp);
        }

        ClientMain.client.modifyQueue(newQueue,ClientMain.client.clientInfo.user_id);
        new SceneChanger().changeScene2("FXML//SongPlayer.fxml","Song",songName);
    }

    public void addSong(ActionEvent actionEvent) {
        List<String > newQueue = list1;
        if (allsongs.isEmpty())
        {
            JOptionPane.showMessageDialog(null,"Select a song to be added");

        }
        else
        {
            String song = selectSongs2CB.getSelectionModel().getSelectedItem().toString();
            newQueue.add(song);
            ClientMain.client.modifyQueue(newQueue,ClientMain.client.clientInfo.user_id);
            new SceneChanger().changeScene2("FXML//SongPlayer.fxml","Song",songName);
        }

    }

    public void fetchLocal(ActionEvent actionEvent) {
        try {
            System.out.println("open song clicked");
            FileChooser chooser = new FileChooser();
            File file = chooser.showOpenDialog(null);
            Media m = new Media(file.toURI().toURL().toString());
            if (player != null) {
                player.dispose();
                slider.adjustValue(0);
            }

            player = new MediaPlayer(m);

            mediaview.setMediaPlayer(player);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public void getNewSongs(ActionEvent actionEvent) {
        new SceneChanger().changeScene2("FXML\\newSongs.fxml","New Songs on server",songName);
    }

    public void deleteSong(ActionEvent actionEvent) {
        List<String > newQueue = list1;
        if (list1.isEmpty())
        {
            JOptionPane.showMessageDialog(null,"Select a song to be added");

        }
        else
        {   try {

            String song = selectSongs2CB.getSelectionModel().getSelectedItem().toString();
            newQueue.remove(song);
            ClientMain.client.modifyQueue(newQueue, ClientMain.client.clientInfo.user_id);
            new SceneChanger().changeScene2("FXML//SongPlayer.fxml", "Song", songName);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        }
    }
}
