package com.example.avatarcertificacao;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;

public class SplashScreen extends Activity{
    
    private Thread mSplashThread; 
    private boolean mblnClicou = false;
 
    /** Evento chamado quando a activity é executada pela primeira vez */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
    
        //thread para mostrar uma tela de Splash
        mSplashThread = new Thread() {
            @Override
            public void run() {
             try {
                    synchronized(this){
                        //Espera por 5 segundos or sai quando
                     //o usuário tocar na tela
                        wait(5000);
                        mblnClicou = true;
                    }
                }
                catch(InterruptedException ex){                    
                }
                 
                if (mblnClicou){
                 //fechar a tela de Splash
                    finish();
                     
                 //Carrega a Activity Principal
                 Intent i = new Intent();
                 i.setClass(SplashScreen.this, LoginScreen.class);
                 startActivity(i);
                }
            }
        };
         
        mSplashThread.start();
    }
     
    @Override
    public void onPause()
    {
        super.onPause();
         
        //garante que quando o usuário clicar no botão
        //"Voltar" o sistema deve finalizar a thread
        mSplashThread.interrupt();
    }
     
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            //o método abaixo está relacionado a thread de splash
         synchronized(mSplashThread){
          mblnClicou = true;
           
             //o método abaixo finaliza o comando wait
             //mesmo que ele não tenha terminado sua espera
                mSplashThread.notifyAll();
            }            
        }
        return true;
    }
 
}