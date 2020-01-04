package ru.otus.kasymbekovPN.zuiNotesCommon.common;

import org.springframework.boot.ApplicationArguments;

import java.util.List;

public class CLArgsParser {

    private final ApplicationArguments args;

    private String status;

    public CLArgsParser(ApplicationArguments args) {
        this.args = args;
        this.status = "";
    }

    public String extractArgAsString(String name){
        String arg = "";
        if (args.containsOption(name)){
            List<String> optionValues = args.getOptionValues(name);
            if (optionValues.size() == 1) {
                arg = optionValues.get(0);
            } else {
                status += name + " contains not one value (or zero, or more than one)";
            }
        } else {
            status += " args don't contain " + name;
        }

        return arg;
    }

    public int extractArgAsInt(String name){
        int arg = 0;
        if (args.containsOption(name)){
            List<String> optionValues = args.getOptionValues(name);
            if (optionValues.size() == 1){
                String sPort = optionValues.get(0);
                try {
                    arg = Integer.parseInt(sPort);
                    if (0 > arg || 65535 < arg){
                        status += " " + name + " = " + arg + " - out of range[0...65535]";
                    }
                } catch (Exception ex){
                    status += name + " has invalid value : " + sPort;
                }
            } else {
                status += name + " contains not one value (or zero, or more than one)";
            }
        } else {
            status += " args don't contain " + name;
        }

        return arg;
    }

    public boolean argsIsValid(){
        return status.isEmpty();
    }

    public String getStatus(){
        return status;
    }
}

