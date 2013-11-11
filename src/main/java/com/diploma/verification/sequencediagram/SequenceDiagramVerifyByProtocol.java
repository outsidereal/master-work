package com.diploma.verification.sequencediagram;
import com.diploma.classdiagram.Method;
import com.diploma.parser.SequenceDiagramParser;
import com.diploma.sequencediagram.Message;
import com.diploma.verification.sequencediagram.SequenceDiagramVerification;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: ksu
 * Date: 11.11.12
 * Time: 16:40
 * To change this template use File | Settings | File Templates.
 */
public class SequenceDiagramVerifyByProtocol extends SequenceDiagramVerification{

    SequenceDiagramParser parserSeq = new SequenceDiagramParser();
    SequenceDiagramVerification verificator = new SequenceDiagramVerification();

    public boolean verifyByProtocol() {
        String messageClass = null;
        parserSeq.parse("D:\\5KURS\\Diplom\\TestDiag\\Diagramms\\src\\my.uml");
        List<Message> listMessage = parserSeq.getMessageList();

        for (Message m : listMessage) {
            System.out.println("list message" + m.getName());
        }

        Map<String,List<Method>> classMethods = verificator.getMethodsWithClass();
        System.out.println("class Methods" + classMethods);
        for (Message message : listMessage) {
            messageClass = parserSeq.getClassByMessage(message, parserSeq.getLifeLineList());
            if(classMethods.containsKey(messageClass)) {
                for (Method method : classMethods.get(messageClass)) {
                    System.out.println("method" + method.getName());
                    System.out.println("message" + message.getName());
                     if (method.getName().equals(message.getName())) {
                         message.setParsed(true);
                         System.out.println(message.getParsed());
                         return true;
                     }
                }
            }
        }
        return false;
    }

}
