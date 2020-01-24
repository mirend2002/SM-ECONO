/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saiton.ccs.printerservice;

import com.saiton.ccs.database.Starter;
import java.util.HashMap;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;

import org.apache.log4j.Logger;
import org.owasp.esapi.codecs.Codec;
import org.owasp.esapi.codecs.OracleCodec;

/**
 *
 * @author nadeesha
 */
public class DirectPrint {

    JasperPrint print;
    public static Starter star;//db connection
    Codec ORACLE_CODEC = new OracleCodec();
    private Logger log = Logger.getLogger(this.getClass());

    public DirectPrint(String fileName, HashMap parameter) {

        try {
            if (star.con == null) {
                JOptionPane.showMessageDialog(null,
                        "Database connection failiure. \n Please contact the"
                                + " administrator.");

            } else {

                print = JasperFillManager.fillReport(fileName, parameter,
                        star.con);

                JasperPrintManager.printReport(print, false);

            }

        } catch (Exception e) {
            log.error("Exception tag --> " + "Jasper error" + e.getMessage());

        }
    }

}
