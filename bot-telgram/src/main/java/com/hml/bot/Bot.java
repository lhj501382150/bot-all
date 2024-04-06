package com.hml.bot;


import java.lang.reflect.Field;
import java.util.Arrays;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.CopyMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import lombok.var;

public class Bot extends TelegramLongPollingBot {
	
	private boolean screaming = false;
	
	//键盘
	private InlineKeyboardMarkup keyboardM1;
	private InlineKeyboardMarkup keyboardM2;

  @Override
  public String getBotUsername() {
      return "mengfantest_bot";
  }

  @Override
  public String getBotToken() {
      return "6724044931:AAE47iAPtnsMxdq8rnhrsJfTbVZNDp4uHuQ";
  }

  @Override
  public void onUpdateReceived(Update update) {
	  System.out.println("-------------");
	  printObj(update);
	  if(update.hasCallbackQuery()) {//回调数据
		  CallbackQuery query = update.getCallbackQuery();
		  Message message = query.getMessage();
		  buttonTap(message.getChatId(), query.getId(), query.getData(), message.getMessageId());
		  return;
	  }
	   Message msg = update.getMessage();
	   Chat chat = msg.getChat();
	   Long chatid = chat.getId();
	   System.out.println(msg);
	   
	   if(msg.isCommand()){ 
		   if(msg.getText().equals("/scream"))         //If the command was /scream, we switch gears
		      screaming = true;
		   else if (msg.getText().equals("/whisper"))  //Otherwise, we return to normal
		      screaming = false;
		   else if(msg.getText().equals("/menu")) {
			   initKeyWord();
			   sendMenu(chatid, "<b>Menu 1</b>", keyboardM1);
			   return ;
		   }
		   //return;                                     //We don't want to echo commands, so we exit
		}
	   
	  
	   if(screaming) {
		   scream(chatid, update.getMessage());
	   }else {
		   copyMessage(chatid, msg.getMessageId());
//		   Message msg = update.getMessage();
//		    var user = msg.getFrom();
//
//		    Chat chat = msg.getChat();
//		    
//		    System.out.println(user.getFirstName() + " wrote " + msg.getText());
//		    
//		    Long id = user.getId();
//		    Long chatid = chat.getId();
//		    sendText(chatid, msg.getText());
	   }
  }
  
  public void scream(Long id,Message msg) {
	  if(msg.hasText())
	       sendText(id, msg.getText().toUpperCase());
	   else
	       copyMessage(id, msg.getMessageId());  //We can't really scream a sticker
  }
  
  public void sendText(Long who, String what){
	   SendMessage sm = SendMessage.builder()
	                    .chatId(who.toString()) //Who are we sending a message to
	                    .text(what).build();    //Message content
	   try {
	        execute(sm);                        //Actually sending the message
	   } catch (TelegramApiException e) {
	        throw new RuntimeException(e);      //Any error will be printed here
	   }
	}
  public void copyMessage(Long who, Integer msgId){
	  CopyMessage cm = CopyMessage.builder()
             .fromChatId(who.toString())  //We copy from the user
          .chatId(who.toString())      //And send it back to him
          .messageId(msgId)            //Specifying what message
          .build();
	   try {
	        execute(cm);                        //Actually sending the message
	   } catch (TelegramApiException e) {
	        throw new RuntimeException(e);      //Any error will be printed here
	   }
	}	
  
  public void sendMenu(Long who, String txt, InlineKeyboardMarkup kb) {
	  SendMessage sm = SendMessage.builder().chatId(who.toString())
	            .parseMode("HTML").text(txt)
	            .replyMarkup(kb).build();

	    try {
	        execute(sm);
	    } catch (TelegramApiException e) {
	        throw new RuntimeException(e);
	    }
  }
  
  public void initKeyWord() {
	  var next = InlineKeyboardButton.builder()
	            .text("Next").callbackData("next")           
	            .build();

	 var back = InlineKeyboardButton.builder()
	            .text("Back").callbackData("back")
	            .build();

	 var url = InlineKeyboardButton.builder()
	            .text("Tutorial")
	            .url("https://core.telegram.org/bots/api")
	            .build();
	 
	  keyboardM1 = InlineKeyboardMarkup.builder()
	          .keyboardRow(Arrays.asList(next)).build();  

	//Buttons are wrapped in lists since each keyboard is a set of button rows
	keyboardM2 = InlineKeyboardMarkup.builder()
	          .keyboardRow(Arrays.asList(back))
	          .keyboardRow(Arrays.asList(url))
	          .build();
  }
  
  private void buttonTap(Long id, String queryId, String data, int msgId) {

	    EditMessageText newTxt = EditMessageText.builder()
	            .chatId(id.toString())
	            .messageId(msgId).text("").build();

	    EditMessageReplyMarkup newKb = EditMessageReplyMarkup.builder()
	            .chatId(id.toString()).messageId(msgId).build();                           

	    if(data.equals("next")) {
	        newTxt.setText("MENU 2");
	        newKb.setReplyMarkup(keyboardM2);
	    } else if(data.equals("back")) {
	        newTxt.setText("MENU 1");
	        newKb.setReplyMarkup(keyboardM1);
	    }

	    AnswerCallbackQuery close = AnswerCallbackQuery.builder()
	            .callbackQueryId(queryId).build();

	    try {
	    	execute(close);
		    execute(newTxt);
		    execute(newKb);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
  
  private <T> void printObj(T obj) {
	  try {
		Field[] fields = obj.getClass().getDeclaredFields();
		for(Field field : fields) {
			field.setAccessible(true);
			Object val = field.get(obj);
			if(val == null) continue;
			System.out.println("【"+field.getName()+"】：" + val);
		}
	} catch (Exception e) {
		e.printStackTrace();
	}
  }
}