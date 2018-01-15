/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bigmovie;

import com.rivescript.macro.Subroutine;
import java.io.File;

import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

/**
 *
 * @author Aswin van Woudenberg
 */
public class SendSubroutine implements Subroutine {
    ListenerAdapter lisAdptr;
    
    public SendSubroutine(ListenerAdapter sender) {
        lisAdptr = sender;
    }
    
    @Override
    public String call(com.rivescript.RiveScript rs, String[] args) {
        String type = args[0];
        if ("photo".equals(type)) {
            String f_id = args[1];
            String caption = "";
            for (int i=2; i<args.length; i++) 
                caption = caption + " " + args[i];
            caption = caption.trim();
            //SendPhoto msg = new SendPhoto()
            //        .setChatId(rs.currentUser())
            //        .setNewPhoto(new File(f_id))
            //        .setCaption(caption);
           // try {
            //    lisAdptr.sendPhoto(msg); // Call method to send the photo with caption
           //} catch (TelegramApiException e) {
           //     e.printStackTrace();
           // }
        }
        return "";
    }
}
