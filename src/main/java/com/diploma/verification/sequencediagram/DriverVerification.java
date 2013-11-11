package com.diploma.verification.sequencediagram;

import com.diploma.parser.SequenceDiagramParser;
import com.diploma.sequencediagram.Fragment;
import com.diploma.sequencediagram.Message;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: ksu
 * Date: 24.02.13
 * Time: 13:01
 * To change this template use File | Settings | File Templates.
 */
public class DriverVerification extends SequenceDiagramVerification {

    List<Message> createMessages = new ArrayList<Message>();
    List<Message> deleteMessages = new ArrayList<Message>();
    SequenceDiagramParser parserSeq = new SequenceDiagramParser();
    SequenceDiagramVerification verification = new SequenceDiagramVerification();
    Fragment findFrag = null;
    public static int errorsCount = 0;

    public boolean verifyByDriver() {
        String messageClass = null;
        parserSeq.parse("D:\\5KURS\\Diplom\\TestDiag\\Diagramms\\src\\new.uml");
        List<Message> listMessage = parserSeq.getMessageList();
        List<Fragment> fragmentList = parserSeq.getFragmentList();

        for (Message m : listMessage) {
            if (m.getMessageSort().equals("createMessage")) {
                createMessages.add(m);
                continue;
            }
            if (m.getMessageSort().equals("deleteMessage"))
                deleteMessages.add(m);
        }

        int sequence = 0; //порядковый номер фрагмента в списке
        for (Message m : createMessages) {
            for (Fragment f : fragmentList) {
                sequence++;
                if (f.getMessage() != null) {
                    if (f.getMessage().equals(m.getId())) {
                        if (f.getName().contains("start") && f.getName().contains("execution")) {
                            findFrag.setCovered(f.getCovered());
                            findFrag.setSequence(sequence);
                            break;
                        }
                    }
                }
            }
            sequence = 0;
            for (Fragment f : fragmentList) {
                sequence++;
                if (f.getCovered().equals(findFrag.getCovered()) && f.getName().contains("start") && f.getName().contains("execution")) {
                    if (sequence < findFrag.getSequence()) {
                        m.setParsed(false);
                        errorsCount++;
                        break;
                    }
                }
            }
        }

        for (Message m : deleteMessages) {
            for (Fragment f : fragmentList) {
                sequence++;
                if (f.getMessage() != null) {
                    if (f.getMessage().equals(m.getId())) {
                        if (f.getName().contains("start") && f.getName().contains("execution")) {
                            findFrag.setCovered(f.getCovered());
                            findFrag.setSequence(sequence);
                            break;
                        }
                    }
                }
            }
            sequence = 0;
            for (Fragment f : fragmentList) {
                sequence++;
                if (f.getCovered().equals(findFrag.getCovered()) &&
                        f.getName().contains("start") &&
                        f.getName().contains("execution")) {
                    if (sequence > findFrag.getSequence()) {
                        m.setParsed(false);
                        errorsCount++;
                        break;
                    }
                }
            }
        }
        if (errorsCount == 0)
            return true;
        else return false;
    }
}
