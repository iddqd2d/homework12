package com.youtube;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.security.GeneralSecurityException;

public class ParseExample {
    private static final Logger LOGGER = Logger.getLogger(ParseExample.class);

    public static void main(String[] videoIds) {
        for (String videoId : videoIds) {
            try {
                LOGGER.info(new MyParseYoutube(videoId).toString());
            } catch (GeneralSecurityException e) {
                LOGGER.error(e.getMessage());
            } catch (IOException e) {
                LOGGER.error(e.getMessage());
            }
        }
    }
}
