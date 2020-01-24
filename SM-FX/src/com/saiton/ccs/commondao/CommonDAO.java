/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saiton.ccs.commondao;


import com.saiton.ccs.database.Starter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import org.apache.log4j.Logger;
import org.owasp.esapi.ESAPI;
import org.owasp.esapi.codecs.Codec;
import org.owasp.esapi.codecs.OracleCodec;

public class CommonDAO {

    public static Starter star;//db connection
    Codec ORACLE_CODEC = new OracleCodec();
    private final Logger log = Logger.getLogger(this.getClass());

    public Boolean insertPayment(
            String TableName,
            String Mbno,
            Double Csh,
            Double CrdCad,
            Double DebCad,
            Double Chk,
            Double Crd,
            Double Adv,
            String Rmk,
            String userId,
            String currency,
            Float rate,
            Double foc) {
        String encodedMbno = ESAPI.encoder().encodeForSQL(ORACLE_CODEC, Mbno);
        String encodedRmk = ESAPI.encoder().encodeForSQL(ORACLE_CODEC, Rmk);
        String encodedUserId = ESAPI.encoder().
                encodeForSQL(ORACLE_CODEC, userId);
        String encodedCurrency = ESAPI.encoder().encodeForSQL(ORACLE_CODEC,
                currency);

        if (star.con == null) {
            log.error("Databse connection failiure.");
            return false;
        } else {
            try {
                String quary = null;
                if (TableName.equalsIgnoreCase(
                        "customer_payment_info_reservation")) {
                    quary = "INSERT INTO customer_payment_info_reservation "
                            + "(`mb_no`, `cash`, `credit_card`, `debit_card`, "
                            + "`cheque`, `credit`, `advance`, `remark`, `user_id`, `currency`, `rate` ,`foc`) "
                            + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,? );";
                } else if (TableName.equalsIgnoreCase(
                        "customer_payment_info_alacate")) {
                    quary = "INSERT INTO customer_payment_info_alacate "
                            + "(`mb_no`, `cash`, `credit_card`, `debit_card`, "
                            + "`cheque`, `credit`, `advance`, `remark`, `user_id`, `currency`, `rate` ,`foc`) "
                            + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? ,?);";
                } else if (TableName.equalsIgnoreCase(
                        "customer_payment_info_alacate_event")) {
                    quary = "INSERT INTO customer_payment_info_alacate_event "
                            + "(`mb_no`, `cash`, `credit_card`, `debit_card`, "
                            + "`cheque`, `credit`, `advance`, `remark`, `user_id`, `currency`, `rate` ,`foc`) "
                            + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? ,?);";
                } else if (TableName.equalsIgnoreCase(
                        "customer_payment_info_sundry")) {
                    quary = "INSERT INTO customer_payment_info_sundry "
                            + "(`mb_no`, `cash`, `credit_card`, `debit_card`, "
                            + "`cheque`, `credit`, `advance`, `remark`, `user_id`, `currency`, `rate` ,`foc`) "
                            + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? ,?);";
                } else if (TableName.equalsIgnoreCase(
                        "customer_payment_info_banquet")) {
                    quary = "INSERT INTO customer_payment_info_banquet "
                            + "(`mb_no`, `cash`, `credit_card`, `debit_card`, "
                            + "`cheque`, `credit`, `advance`, `remark`, `user_id`, `currency`, `rate` ,`foc`) "
                            + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,? );";
                } else if (TableName.equalsIgnoreCase(
                        "customer_payment_info_room")) {
                    quary = "INSERT INTO customer_payment_info_room "
                            + "(`mb_no`, `cash`, `credit_card`, `debit_card`, "
                            + "`cheque`, `credit`, `advance`, `remark`, `user_id`, `currency`, `rate` ,`foc`) "
                            + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,? );";
                }

                PreparedStatement ps = star.con.prepareStatement(quary);
                ps.setString(1, encodedMbno);
                ps.setDouble(2, Csh);
                ps.setDouble(3, CrdCad);
                ps.setDouble(4, DebCad);
                ps.setDouble(5, Chk);
                ps.setDouble(6, Crd);
                ps.setDouble(7, Adv);
                ps.setString(8, encodedRmk);
                ps.setString(9, encodedUserId);
                ps.setString(10, encodedCurrency);
                ps.setFloat(11, rate);
                ps.setDouble(12, foc);

                int val = ps.executeUpdate();
                return val == 1;

            } catch (NullPointerException | SQLException e) {

                if (e instanceof NullPointerException) {
                    log.error("Exception tag --> " + "Empty entry passed");
                } else if (e instanceof SQLException) {
                    log.error("Exception tag --> " + "Invalid sql statement "
                            + e.getMessage());
                }
                return false;
            } catch (Exception e) {
                log.error("Exception tag --> " + "Error");
                return false;
            }
        }
    }

    public ArrayList<String> loadRoomLocationId() {

        String extraType = null;
        ArrayList list = new ArrayList();

        if (star.con == null) {
            log.error(" Exception tag --> " + "Databse connection failiure. ");
            return null;

        } else {
            try {

                String query = "SELECT * FROM room_location ";
                PreparedStatement pstmt = star.con.prepareStatement(query);
                ResultSet r = pstmt.executeQuery();

                while (r.next()) {
                    extraType = r.getString("location");
                    list.add(extraType);

                }

            } catch (ArrayIndexOutOfBoundsException | SQLException |
                    NullPointerException e) {

                if (e instanceof ArrayIndexOutOfBoundsException) {
                    log.error("Exception tag --> "
                            + "Invalid entry location for list");
                } else if (e instanceof SQLException) {
                    log.error("Exception tag --> " + "Invalid sql statement");
                } else if (e instanceof NullPointerException) {
                    log.error("Exception tag --> " + "Empty entry for list");
                }
                return null;
            } catch (Exception e) {
                log.error("Exception tag --> " + "Error");
                return null;
            }
        }
        return list;
    }

    //From KotAlacarteDAO
    public ArrayList loadPrinterInfo() {

        String tableNo = null;
        ArrayList tablenoList = new ArrayList();

        if (star.con == null) {
            log.error("Databse connection failiure.");
        } else {
            try {
                Statement stt = star.con.createStatement();
                ResultSet r = stt.
                        executeQuery("SELECT * FROM ala_print_remarks");
                while (r.next()) {
                    tableNo = r.getString("print_info");

                    tablenoList.add(tableNo);
                }

            } catch (ArrayIndexOutOfBoundsException | SQLException |
                    NullPointerException e) {
                if (e instanceof ArrayIndexOutOfBoundsException) {
                    log.error("Exception tag --> "
                            + "Invalid entry location for list");
                } else if (e instanceof SQLException) {
                    log.error("Exception tag --> " + "Invalid sql statement "
                            + e.getMessage());
                } else if (e instanceof NullPointerException) {
                    log.error("Exception tag --> " + "Empty entry for list");
                }
                return null;
            } catch (Exception e) {
                log.error("Exception tag --> " + "Error");
                return null;
            }
        }
        return tablenoList;
    }

    public boolean isPrinterInfoAvailable(String desc) {

        String encodedDesc = ESAPI.encoder().encodeForSQL(ORACLE_CODEC, desc);
        boolean available = false;

        if (star.con == null) {

            log.error("Exception tag --> " + "Databse connection failiure. ");

        } else {
            try {

                String query
                        = "SELECT * FROM ala_print_remarks WHERE print_info = ? ";

                PreparedStatement pstmt = star.con.prepareStatement(query);
                pstmt.setString(1, encodedDesc);
                ResultSet r = pstmt.executeQuery();

                while (r.next()) {
                    available = true;
                }
                if (available == false) {
                    insertPrinterInfo(desc);
                }

            } catch (NullPointerException | SQLException e) {
                if (e instanceof NullPointerException) {
                    log.error("Exception tag --> " + "Empty entry passed");

                } else if (e instanceof SQLException) {
                    log.error("Exception tag --> " + "Invalid sql statement");
                }
                return false;
            } catch (Exception e) {
                log.error("Exception tag --> " + "Error");
                return false;
            }
        }
        return available;
    }

    public boolean insertPrinterInfo(
            String remarks) {

        String encodedRemarks = ESAPI.encoder().encodeForSQL(ORACLE_CODEC,
                remarks);

        if (star.con == null) {

            log.info("Exception tag --> " + "Databse connection failiure. ");
            return false;
        } else {
            try {

                PreparedStatement ps = star.con.prepareStatement(
                        "INSERT INTO ala_print_remarks(print_info) VALUES(?)");
                ps.setString(1, encodedRemarks);

                int val = ps.executeUpdate();
                return val == 1;

            } catch (NullPointerException | SQLException e) {

                if (e instanceof NullPointerException) {

                    log.info("Exception tag --> " + "Empty entry passed");

                } else if (e instanceof SQLException) {

                    log.info("Exception tag --> " + "Invalid sql statement");

                }
                return false;
            } catch (Exception e) {

                log.info("Exception tag --> " + "Error");

                return false;
            }
        }
    }

    public boolean isPrinterInfoAvailableToDelete(String itemId) {

        String encodedItemId = ESAPI.encoder().
                encodeForSQL(ORACLE_CODEC, itemId);
        boolean available = false;

        if (star.con == null) {

            log.error("Exception tag --> " + "Databse connection failiure. ");

        } else {
            try {

                String query
                        = "SELECT * FROM ala_print_remarks WHERE print_info= ? ";

                PreparedStatement pstmt = star.con.prepareStatement(query);
                pstmt.setString(1, encodedItemId);
                ResultSet r = pstmt.executeQuery();

                while (r.next()) {
                    available = true;
                }

            } catch (NullPointerException | SQLException e) {
                if (e instanceof NullPointerException) {
                    log.error("Exception tag --> " + "Empty entry passed");

                } else if (e instanceof SQLException) {
                    log.error("Exception tag --> " + "Invalid sql statement");
                }
                return false;
            } catch (Exception e) {
                log.error("Exception tag --> " + "Error");
                return false;
            }
        }
        return available;
    }

    public boolean deletePrinterInfo(String remarks) {

        String encodedRemarks = ESAPI.encoder().encodeForSQL(ORACLE_CODEC,
                remarks);

        if (star.con == null) {
            log.error("Databse connection failiure.");
            return false;
        } else {
            try {

                String query
                        = "DELETE FROM ala_print_remarks WHERE print_info=?";

                PreparedStatement pre = star.con.prepareStatement(query);
                pre.setString(1, encodedRemarks);

                int val = pre.executeUpdate();
                return val == 1;

            } catch (NullPointerException | SQLException e) {
                if (e instanceof NullPointerException) {
                    log.error("Exception tag --> " + "Empty entry passed");
                } else if (e instanceof SQLException) {
                    log.error("Exception tag --> " + "Invalid sql statement "
                            + e.getMessage());
                }
                return false;
            } catch (Exception e) {
                log.error("Exception tag --> " + "Error");
                return false;
            }
        }
    }

    /////////////////////////////////////////////////////////////////////////
    public String loadAdvanceAmountInfo(String advanceId) {
        String encodedAdvanceId = ESAPI.encoder().encodeForSQL(ORACLE_CODEC,
                advanceId);
        String category = null;

        if (star.con == null) {
            log.error(" Exception tag --> " + "Databse connection failiure. ");
            return null;

        } else {
            try {

                String query
                        = "SELECT amount - settle_amount as tot FROM customer_advance where advance_id=? and setOff=0 ";
                PreparedStatement pstmt = star.con.prepareStatement(query);
                pstmt.setString(1, encodedAdvanceId);
                ResultSet r = pstmt.executeQuery();

                while (r.next()) {
                    category = r.getString("tot");

                }

            } catch (ArrayIndexOutOfBoundsException | SQLException |
                    NullPointerException e) {

                if (e instanceof ArrayIndexOutOfBoundsException) {
                    log.error("Exception tag --> "
                            + "Invalid entry location for list");
                } else if (e instanceof SQLException) {
                    log.error("Exception tag --> " + "Invalid sql statement");
                } else if (e instanceof NullPointerException) {
                    log.error("Exception tag --> " + "Empty entry for list");
                }
                return null;
            } catch (Exception e) {
                log.error("Exception tag --> " + "Error");
                return null;
            }
        }
        return category;
    }

    // From CustomerRegistrationDAO
    public ArrayList<ArrayList<String>> searchItemDetails(String search) {

        String encodedSearch = ESAPI.encoder().
                encodeForSQL(ORACLE_CODEC, search);

        String cus_id = null;
        String cus_name = null;
        String cus_address = null;
        String cus_nic = null;
        String cus_title = null;

        ArrayList<ArrayList<String>> Mainlist = new ArrayList<>();

        if (star.con == null) {

            log.info(" Exception tag --> " + "Databse connection failiure. ");
            return null;

        } else {
            try {

                String query = "SELECT * FROM customer WHERE "
                        + "(cus_name LIKE ? OR cus_address LIKE ? "
                        + "OR cus_id LIKE ? OR cus_address LIKE  ? "
                        + "OR cus_nic_passport_id_Type LIKE ? )";

                PreparedStatement pstmt = star.con.prepareStatement(query);
                pstmt.setString(1, encodedSearch + "%");
                pstmt.setString(2, encodedSearch + "%");
                pstmt.setString(3, encodedSearch + "%");
                pstmt.setString(4, encodedSearch + "%");
                pstmt.setString(5, encodedSearch + "%");

                ResultSet r = pstmt.executeQuery();

                while (r.next()) {

                    ArrayList<String> list = new ArrayList<>();

                    cus_id = r.getString("cus_id");
                    cus_title = r.getString("cus_title");
                    cus_name = r.getString("cus_name");
                    cus_nic = r.getString("cus_nic_passport_id_Type");
                    cus_address = r.getString("cus_address");

                    list.add(cus_id);
                    list.add(cus_title);
                    list.add(cus_name);
                    list.add(cus_nic);
                    list.add(cus_address);

                    Mainlist.add(list);

                }

            } catch (ArrayIndexOutOfBoundsException | SQLException |
                    NullPointerException e) {

                if (e instanceof ArrayIndexOutOfBoundsException) {

                    log.error("Exception tag --> "
                            + "Invalid entry location for list");

                } else if (e instanceof SQLException) {

                    log.error("Exception tag --> " + "Invalid sql statement");

                } else if (e instanceof NullPointerException) {

                    log.error("Exception tag --> " + "Empty entry for list");

                }
                return null;
            } catch (Exception e) {

                log.error("Exception tag --> " + "Error");

                return null;
            }
        }
        return Mainlist;
    }

    ////////////////////////////////////////////////////
    //From FunctionRegistrationDAO
    public ArrayList<String> loadEventInfo(String cusId, String eventId) {

        String encodedEventId = ESAPI.encoder().encodeForSQL(ORACLE_CODEC,
                eventId);
        String encodedCusId = ESAPI.encoder().encodeForSQL(ORACLE_CODEC, cusId);
        ArrayList<String> list = new ArrayList<>();
        String occasion = null;
        String eventName = null;
        String date = null;
        String paxCount = null;
        String hallId = null;
        String description = null;
        String contactName = null;
        String contactMob = null;
        String contactTel = null;
        String contactTitle = null;
        String fromTime = null;
        String toTime = null;

        if (star.con == null) {
            log.error("Databse connection failiure.");
            return null;
        } else {

            try {

                String query = "SELECT * FROM event_reg er JOIN event_log el "
                        + "ON er.event_id = el.event_id WHERE er.event_id = ? "
                        + "AND er.cus_id = ?";

                PreparedStatement pstmt = star.con.prepareStatement(query);
                pstmt.setString(1, encodedEventId);
                pstmt.setString(2, encodedCusId);

                ResultSet r = pstmt.executeQuery();

                while (r.next()) {

                    occasion = r.getString("er.event_title");
                    eventName = r.getString("el.event_log_event_name");
                    date = r.getString("el.event_log_date");
                    paxCount = r.getString("el.event_log_pax_count");
                    hallId = r.getString("el.hall_id");
                    description = r.getString("el.event_log_event_description");
                    contactName = r.getString("el.event_log_contact_Name");
                    contactMob = r.getString("el.event_log_contact_mob");
                    contactTel = r.getString("el.event_log_contact_tel");
                    contactTitle = r.getString("el.event_log_contact_title");
                    fromTime = r.getString("el.event_log_time_from");
                    toTime = r.getString("el.event_log_time_to");

                    list.add(occasion);
                    list.add(eventName);
                    list.add(date);
                    list.add(paxCount);
                    list.add(hallId);
                    list.add(description);
                    list.add(contactName);
                    list.add(contactMob);
                    list.add(contactTel);
                    list.add(contactTitle);
                    list.add(fromTime);
                    list.add(toTime);

                }

            } catch (ArrayIndexOutOfBoundsException | SQLException |
                    NullPointerException e) {
                if (e instanceof ArrayIndexOutOfBoundsException) {
                    log.error("Exception tag --> "
                            + "Invalid entry location for list");
                } else if (e instanceof SQLException) {
                    log.error("Exception tag --> " + "Invalid sql statement "
                            + e.getMessage());
                } else if (e instanceof NullPointerException) {
                    log.error("Exception tag --> " + "Empty entry for list");
                }
                return null;
            } catch (Exception e) {
                log.error("Exception tag --> " + "Error");
                return null;
            }
        }
        return list;
    }

    public double getExtraKotBotTotal(String eventId,
            String cusId, String type) {

        String encodedCusID = ESAPI.encoder().encodeForSQL(ORACLE_CODEC, cusId);
        String encodedEventID = ESAPI.encoder().encodeForSQL(ORACLE_CODEC,
                eventId);
        String encodedType = ESAPI.encoder().encodeForSQL(ORACLE_CODEC, type);

        double value = 0.0;
        double price = 0.0;
        double qty = 0.0;
        double rate = 0.0;

        if (star.con == null) {
            log.error("Databse connection failiure.");

        } else {
            try {

                String query
                        = " SELECT * FROM extra_kot_bot kb JOIN"
                        + " ( item i,item_sub s ) ON "
                        + " kb.kot_bot_item_id=i.item_id AND"
                        + " kb.kot_bot_item_id = s.item_id "
                        + " AND kb.batch_no=s.batch_no "
                        + " WHERE kb.event_id = ? AND"
                        + " kb.cus_id = ? AND "
                        + " kb.kot_bot_type = ? ";
                PreparedStatement pstmt = star.con.prepareStatement(query);
                pstmt.setString(1, encodedEventID);
                pstmt.setString(2, encodedCusID);
                pstmt.setString(3, encodedType);
                ResultSet r = pstmt.executeQuery();
                while (r.next()) {

                    price = r.getDouble("kb.kot_bot_price");
                    qty = r.getDouble("kb.kot_bot_qty");
                    rate = r.getDouble("kb.kot_bot_rate");

                    value += ((price * qty) * (rate + 100)) / 100;

                }
            } catch (ArrayIndexOutOfBoundsException | SQLException |
                    NullPointerException e) {
                if (e instanceof ArrayIndexOutOfBoundsException) {
                    log.error("Exception tag --> "
                            + "Invalid entry location for list");
                } else if (e instanceof SQLException) {
                    log.error("Exception tag --> " + "Invalid sql statement "
                            + e.getMessage());
                } else if (e instanceof NullPointerException) {
                    log.error("Exception tag --> " + "Empty entry for list");
                }
                return 0.0;
            } catch (Exception e) {
                log.error("Exception tag --> " + "Error");
                return 0.0;
            }
        }
        return value;
    }

    public boolean isEventAvailable(String eventId) {

        String encodedEventId = ESAPI.encoder().encodeForSQL(ORACLE_CODEC,
                eventId);
        boolean available = false;

        if (star.con == null) {
            log.error("Databse connection failiure.");
            return false;
        } else {

            try {

                String query
                        = "SELECT * FROM event_log where event_id = ? AND status = '1'";

                PreparedStatement pstmt = star.con.prepareStatement(query);
                pstmt.setString(1, encodedEventId);

                ResultSet r = pstmt.executeQuery();

                while (r.next()) {
                    available = true;
                }
            } catch (NullPointerException | SQLException e) {
                if (e instanceof NullPointerException) {
                    log.error("Exception tag --> " + "Empty entry passed");

                } else if (e instanceof SQLException) {
                    log.error("Exception tag --> " + "Invalid sql statement "
                            + e.getMessage());
                }
                return false;
            } catch (Exception e) {
                log.error("Exception tag --> " + "Error");
                return false;
            }
        }
        return available;
    }

    public Double getAdvanceTotal(String EventID, String CusID) {

        String encodedCusID = ESAPI.encoder().encodeForSQL(ORACLE_CODEC, CusID);
        String encodedEventID = ESAPI.encoder().encodeForSQL(ORACLE_CODEC,
                EventID);
        double sum = 0;

        if (star.con == null) {
            log.error("Databse connection failiure.");
            return null;
        } else {

            try {
                Statement stt = star.con.createStatement();
                ResultSet r = stt.executeQuery(
                        "SELECT SUM(adv_amount_paid) FROM advance "
                        + "WHERE event_id = '"
                        + encodedEventID + "'AND cus_id = '" + encodedCusID
                        + "'");
                while (r.next()) {
                    sum = r.getDouble(1);
                }
            } catch (NullPointerException | SQLException e) {
                if (e instanceof NullPointerException) {
                    log.error("Exception tag --> " + "Empty entry passed");
                } else if (e instanceof SQLException) {
                    log.error("Exception tag --> " + "Invalid sql statement "
                            + e.getMessage());
                }
                return null;
            } catch (Exception e) {
                log.error("Exception tag --> " + "Error");
                return null;
            }
        }
        return sum;
    }

    ///////////////////////////////////////////////////////////////////
    public String getCategory(int id) {

        String category = null;
        if (star.con == null) {
            log.info(" Exception tag --> " + "Databse connection failiure. ");
            return category;
        } else {
            try {

                String query = "SELECT category from room_category where id= ? ";

                PreparedStatement pstmt = star.con.prepareStatement(query);
                pstmt.setInt(1, id);

                ResultSet r = pstmt.executeQuery();

                while (r.next()) {
                    category = r.getString("category");
                }

            } catch (ArrayIndexOutOfBoundsException | SQLException |
                    NullPointerException e) {

                if (e instanceof ArrayIndexOutOfBoundsException) {

                    log.error(
                            "Exception tag --> "
                            + "Invalid entry location for list");

                } else if (e instanceof SQLException) {

                    log.error("Exception tag --> " + "Invalid sql statement");

                } else if (e instanceof NullPointerException) {

                    log.error("Exception tag --> " + "Empty entry for list");

                }
                return category;
            } catch (Exception e) {

                log.error("Exception tag --> " + "Error");

                return category;
            }
        }
        return category;
    }

    public Boolean updateAdvance(Double payment, int Status, String advanceId) {
        String encodedAdvanceId = ESAPI.encoder().encodeForSQL(ORACLE_CODEC,
                advanceId);

        if (star.con == null) {
            log.error("Databse connection failiure.");
            return null;
        } else {
            try {
                String query
                        = "UPDATE customer_advance set settle_amount= settle_amount + ? , setoff= ?  WHERE advance_id=? ";

                PreparedStatement pstmt = star.con.prepareStatement(query);
                pstmt.setDouble(1, payment);
                pstmt.setInt(2, Status);
                pstmt.setString(3, encodedAdvanceId);

                int val = pstmt.executeUpdate();

                if (val == 1) {
                    return true;
                } else {
                    return false;
                }

            } catch (NullPointerException | NumberFormatException | SQLException e) {

                if (e instanceof NullPointerException) {
                    log.error("Exception tag --> " + "Empty entry passed");
                } else if (e instanceof NumberFormatException) {
                    log.error("Exception tag --> "
                            + "Invalid number found in current id");
                } else if (e instanceof SQLException) {
                    log.error("Exception tag --> " + "Invalid sql statement "
                            + e.getMessage());
                }
                return null;
            } catch (Exception e) {
                log.error("Exception tag --> " + "Error");
                return null;
            }
        }
    }

    public Boolean ResetAdvance(Double payment, String advanceId) {
        String encodedAdvanceId = ESAPI.encoder().encodeForSQL(ORACLE_CODEC,
                advanceId);

        if (star.con == null) {
            log.error("Databse connection failiure.");
            return null;
        } else {
            try {
                String query
                        = "UPDATE customer_advance set settle_amount= settle_amount - ? , setoff= 0  WHERE advance_id=? ";

                PreparedStatement pstmt = star.con.prepareStatement(query);
                pstmt.setDouble(1, payment);
                pstmt.setString(2, encodedAdvanceId);

                int val = pstmt.executeUpdate();

                if (val == 1) {
                    return true;
                } else {
                    return false;
                }

            } catch (NullPointerException | NumberFormatException | SQLException e) {

                if (e instanceof NullPointerException) {
                    log.error("Exception tag --> " + "Empty entry passed");
                } else if (e instanceof NumberFormatException) {
                    log.error("Exception tag --> "
                            + "Invalid number found in current id");
                } else if (e instanceof SQLException) {
                    log.error("Exception tag --> " + "Invalid sql statement "
                            + e.getMessage());
                }
                return null;
            } catch (Exception e) {
                log.error("Exception tag --> " + "Error");
                return null;
            }
        }
    }

    public ArrayList<String> loadAdvanceReceiptInfo(String cusId,
            boolean iscustomer) {
        String encodedCusId = ESAPI.encoder().encodeForSQL(ORACLE_CODEC, cusId);
        String category = null;

        ArrayList list = new ArrayList();

        if (star.con == null) {
            log.error(" Exception tag --> " + "Databse connection failiure. ");
            return null;

        } else {
            try {
                String query = null;
                if (iscustomer) {
                    query = "SELECT c.advance_id "
                            + "FROM customer_advance_cus c "
                            + "inner join customer_advance d "
                            + "on d.advance_id=c.advance_id "
                            + "where c.cus_id=? and d.setoff=0 ";
                } else {
                    query = "SELECT c.advance_id "
                            + "FROM customer_advance_agent c "
                            + "inner join customer_advance d "
                            + "on d.advance_id=c.advance_id "
                            + "where c.agent_id=? and d.setoff=0 ";
                }

                PreparedStatement pstmt = star.con.prepareStatement(query);
                pstmt.setString(1, encodedCusId);
                ResultSet r = pstmt.executeQuery();

                while (r.next()) {
                    category = r.getString("advance_id");
                    list.add(category);

                }

            } catch (ArrayIndexOutOfBoundsException | SQLException |
                    NullPointerException e) {

                if (e instanceof ArrayIndexOutOfBoundsException) {
                    log.error("Exception tag --> "
                            + "Invalid entry location for list");
                } else if (e instanceof SQLException) {
                    log.error("Exception tag --> " + "Invalid sql statement");
                } else if (e instanceof NullPointerException) {
                    log.error("Exception tag --> " + "Empty entry for list");
                }
                return null;
            } catch (Exception e) {
                log.error("Exception tag --> " + "Error");
                return null;
            }
        }
        return list;
    }

    public ArrayList<ArrayList<String>> loadAdvanceReceiptInfoAll(String cusId,
            boolean iscustomer) {
        String encodedCusId = ESAPI.encoder().encodeForSQL(ORACLE_CODEC, cusId);
        String advance_id = null;
        String advance_type = null;
        String amount = null;

        ArrayList<ArrayList<String>> Mainlist
                = new ArrayList<ArrayList<String>>();

        if (star.con == null) {
            log.error(" Exception tag --> " + "Databse connection failiure. ");
            return null;

        } else {
            try {
                String query = null;
                if (iscustomer) {
                    query = "SELECT c.advance_id,d.advance_type,d.amount "
                            + "FROM customer_advance_cus c "
                            + "inner join customer_advance d "
                            + "on d.advance_id=c.advance_id "
                            + "where c.cus_id=? and d.setoff=0 ";
                } else {
                    query = "SELECT c.advance_id,d.advance_type,d.amount "
                            + "FROM customer_advance_agent c "
                            + "inner join customer_advance d "
                            + "on d.advance_id=c.advance_id "
                            + "where c.agent_id=? and d.setoff=0 ";
                }

                PreparedStatement pstmt = star.con.prepareStatement(query);
                pstmt.setString(1, encodedCusId);
                ResultSet r = pstmt.executeQuery();

                while (r.next()) {

                    ArrayList<String> list = new ArrayList<String>();

                    advance_id = r.getString("advance_id");
                    advance_type = r.getString("advance_type");
                    amount = r.getString("amount");

                    list.add(advance_id);
                    list.add(advance_type);
                    list.add(amount);

                    Mainlist.add(list);

                }

            } catch (ArrayIndexOutOfBoundsException | SQLException |
                    NullPointerException e) {

                if (e instanceof ArrayIndexOutOfBoundsException) {
                    log.error("Exception tag --> "
                            + "Invalid entry location for list");
                } else if (e instanceof SQLException) {
                    log.error("Exception tag --> " + "Invalid sql statement "
                            + e.getMessage());
                } else if (e instanceof NullPointerException) {
                    log.error("Exception tag --> " + "Empty entry for list");
                }
                return null;
            } catch (Exception e) {
                log.error("Exception tag --> " + "Error");
                return null;
            }
        }
        return Mainlist;
    }

    public ArrayList<String> getAdvanceDetail(String advanceId) {
        String encodedAdvanceId = ESAPI.encoder().encodeForSQL(ORACLE_CODEC,
                advanceId);
        String category = null;
        String currency = null;
        String rate = null;

        ArrayList list = new ArrayList();

        if (star.con == null) {
            log.error(" Exception tag --> " + "Databse connection failiure. ");
            return null;

        } else {
            try {

                String query
                        = "SELECT * FROM customer_advance where advance_id =? and setoff=0 ";
                PreparedStatement pstmt = star.con.prepareStatement(query);
                pstmt.setString(1, encodedAdvanceId);
                ResultSet r = pstmt.executeQuery();

                while (r.next()) {
                    category = r.getString("advance_id");
                    currency = r.getString("currency");
                    rate = r.getString("rate");
                    list.add(category);
                    list.add(currency);
                    list.add(rate);

                }

            } catch (ArrayIndexOutOfBoundsException | SQLException |
                    NullPointerException e) {

                if (e instanceof ArrayIndexOutOfBoundsException) {
                    log.error("Exception tag --> "
                            + "Invalid entry location for list");
                } else if (e instanceof SQLException) {
                    log.error("Exception tag --> " + "Invalid sql statement");
                } else if (e instanceof NullPointerException) {
                    log.error("Exception tag --> " + "Empty entry for list");
                }
                return null;
            } catch (Exception e) {
                log.error("Exception tag --> " + "Error");
                return null;
            }
        }
        return list;
    }

    public Boolean insertPaymentItems(
            String tableName,
            String mbno,
            String paymeth,
            String remarks,
            Double payment
    ) {
        String encodedMbno = ESAPI.encoder().encodeForSQL(ORACLE_CODEC, mbno);
        String encodedPaymeth = ESAPI.encoder().encodeForSQL(ORACLE_CODEC,
                paymeth);
        String encodedRemarks = ESAPI.encoder().encodeForSQL(ORACLE_CODEC,
                remarks);

        if (star.con == null) {
            log.error("Databse connection failiure.");
            return false;
        } else {
            try {
                String quary = null;
                if (tableName.equalsIgnoreCase("customer_payment_alacate")) {
                    quary = "INSERT INTO customer_payment_alacate "
                            + "(`mb_no`, `paymeth`, `remark`, `payment`) VALUES (?, ?, ?, ?)";
                } else if (tableName.equalsIgnoreCase(
                        "customer_payment_alacate_event")) {
                    quary = "INSERT INTO customer_payment_alacate_event "
                            + "(`mb_no`, `paymeth`, `remark`, `payment`) VALUES (?, ?, ?, ?)";
                } else if (tableName.equalsIgnoreCase("customer_payment_sundry")) {
                    quary = "INSERT INTO customer_payment_sundry "
                            + "(`mb_no`, `paymeth`, `remark`, `payment`) VALUES (?, ?, ?, ?)";
                } else if (tableName.
                        equalsIgnoreCase("customer_payment_banquet")) {
                    quary = "INSERT INTO customer_payment_banquet "
                            + "(`mb_no`, `paymeth`, `remark`, `payment`) VALUES (?, ?, ?, ?)";
                } else if (tableName.equalsIgnoreCase("customer_payment_room")) {
                    quary = "INSERT INTO customer_payment_room "
                            + "(`mb_no`, `paymeth`, `remark`, `payment`) VALUES (?, ?, ?, ?)";
                }
                PreparedStatement ps = star.con.prepareStatement(quary);
                ps.setString(1, encodedMbno);
                ps.setString(2, encodedPaymeth);
                ps.setString(3, encodedRemarks);
                ps.setDouble(4, payment);

                int val = ps.executeUpdate();
                if (val == 1) {
                    return true;
                } else {
                    return false;
                }

            } catch (NullPointerException | SQLException e) {

                if (e instanceof NullPointerException) {
                    log.error("Exception tag --> " + "Empty entry passed");
                } else if (e instanceof SQLException) {
                    log.error("Exception tag --> " + "Invalid sql statement "
                            + e.getMessage());
                }
                return false;
            } catch (Exception e) {
                log.error("Exception tag --> " + "Error");
                return false;
            }
        }
    }

    public Boolean insertCreditPayment(
            String tableName,
            String mbno,
            String mbType,
            String cusId,
            String creditType,
            Double payment,
            String date,
            String userId,
            String Currency) {
        String encodedMbno = ESAPI.encoder().encodeForSQL(ORACLE_CODEC, mbno);
        String encodedMbType = ESAPI.encoder().
                encodeForSQL(ORACLE_CODEC, mbType);
        String encodedCusId = ESAPI.encoder().encodeForSQL(ORACLE_CODEC, cusId);
        String encodedcreditType = ESAPI.encoder().encodeForSQL(ORACLE_CODEC,
                creditType);
        String encodedDate = ESAPI.encoder().encodeForSQL(ORACLE_CODEC, date);
        String encodedUserId = ESAPI.encoder().
                encodeForSQL(ORACLE_CODEC, userId);
        String encodedCurrency = ESAPI.encoder().encodeForSQL(ORACLE_CODEC,
                Currency);

        if (star.con == null) {
            log.error("Databse connection failiure.");
            return false;
        } else {
            try {
                String quary = null;
                if (tableName.equalsIgnoreCase("customer_outstanding_alacate")) {
                    quary = "INSERT INTO customer_outstanding_alacate "
                            + "(`mb_id`, `mb_type`, `cus_id`, `credit_type`, `amount`, `date`, `user_id`, `currency`) "
                            + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
                } else if (tableName.equalsIgnoreCase(
                        "customer_outstanding_alacate_event")) {
                    quary = "INSERT INTO customer_outstanding_alacate_event "
                            + "(`mb_id`, `mb_type`, `cus_id`, `credit_type`, `amount`, `date`, `user_id`, `currency`) "
                            + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
                } else if (tableName.equalsIgnoreCase(
                        "customer_outstanding_banquet")) {
                    quary = "INSERT INTO customer_outstanding_banquet "
                            + "(`mb_id`, `mb_type`, `cus_id`, `credit_type`, `amount`, `date`, `user_id`, `currency`) "
                            + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
                } else if (tableName.equalsIgnoreCase(
                        "customer_outstanding_reservation")) {
                    quary = "INSERT INTO customer_outstanding_reservation "
                            + "(`mb_id`, `mb_type`, `cus_id`, `credit_type`, `amount`, `date`, `user_id`, `currency`) "
                            + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
                } else if (tableName.equalsIgnoreCase(
                        "customer_outstanding_room")) {
                    quary = "INSERT INTO customer_outstanding_room "
                            + "(`mb_id`, `mb_type`, `credit_type`, `amount`, `date`, `user_id`, `currency`) "
                            + "VALUES (?, ?, ?, ?, ?, ?, ?)";
                } else if (tableName.equalsIgnoreCase(
                        "customer_outstanding_sundry")) {
                    quary = "INSERT INTO customer_outstanding_sundry "
                            + "(`mb_id`, `mb_type`, `cus_id`, `credit_type`, `amount`, `date`, `user_id`, `currency`) "
                            + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
                }
                PreparedStatement ps = star.con.prepareStatement(quary);
                if (tableName.equalsIgnoreCase("customer_outstanding_room")) {
                    ps.setString(1, encodedMbno);
                    ps.setString(2, encodedMbType);
                    ps.setString(3, encodedcreditType);
                    ps.setDouble(4, payment);
                    ps.setString(5, date);
                    ps.setString(6, encodedUserId);
                    ps.setString(7, encodedCurrency);
                } else {

                    ps.setString(1, encodedMbno);
                    ps.setString(2, encodedMbType);
                    ps.setString(3, encodedCusId);
                    ps.setString(4, encodedcreditType);
                    ps.setDouble(5, payment);
                    ps.setString(6, date);
                    ps.setString(7, encodedUserId);
                    ps.setString(8, encodedCurrency);
                }

                int val = ps.executeUpdate();
                if (val == 1) {
                    return true;
                } else {
                    return false;
                }

            } catch (NullPointerException | SQLException e) {

                if (e instanceof NullPointerException) {
                    log.error("Exception tag --> " + "Empty entry passed");
                } else if (e instanceof SQLException) {
                    log.error("Exception tag --> " + "Invalid sql statement "
                            + e.getMessage());
                }
                return false;
            } catch (Exception e) {
                log.error("Exception tag --> " + "Error");
                return false;
            }
        }
    }

    public ArrayList<ArrayList<String>> fillPaymentInfo(String tableName,
            String masterBillId) {

        String paymeth = null;
        String remark = null;
        String payment = null;

        ArrayList<ArrayList<String>> Mainlist
                = new ArrayList<ArrayList<String>>();
        String encodedMasterBillId = ESAPI.encoder().encodeForSQL(ORACLE_CODEC,
                masterBillId);

        if (star.con == null) {
            log.error("Databse connection failiure.");

        } else {

            try {
                String quary = null;
                if (tableName.equalsIgnoreCase("customer_payment_alacate")) {
                    quary = "SELECT * FROM "
                            + " customer_payment_alacate where mb_no=? ";
                } else if (tableName.equalsIgnoreCase(
                        "customer_payment_alacate_event")) {
                    quary = "SELECT * FROM "
                            + " customer_payment_alacate_event where mb_no=? ";
                } else if (tableName.equalsIgnoreCase("customer_payment_sundry")) {
                    quary = "SELECT * FROM "
                            + " customer_payment_sundry where mb_no=? ";
                } else if (tableName.
                        equalsIgnoreCase("customer_payment_banquet")) {
                    quary = "SELECT * FROM "
                            + " customer_payment_banquet where mb_no=? ";
                } else if (tableName.equalsIgnoreCase("customer_payment_room")) {
                    quary = "SELECT * FROM "
                            + " customer_payment_room where mb_no=? ";
                }

                PreparedStatement pstmt = star.con.prepareStatement(quary);
                pstmt.setString(1, encodedMasterBillId);

                ResultSet r = pstmt.executeQuery();

                while (r.next()) {
                    ArrayList<String> list = new ArrayList<String>();

                    paymeth = r.getString("paymeth");
                    remark = r.getString("remark");
                    payment = r.getString("payment");

                    list.add(paymeth);
                    list.add(remark);
                    list.add(payment);
                    Mainlist.add(list);
                }

            } catch (ArrayIndexOutOfBoundsException | NullPointerException |
                    NumberFormatException | SQLException e) {

                if (e instanceof ArrayIndexOutOfBoundsException) {
                    log.error("Exception tag --> "
                            + "Invalid entry location for list");
                } else if (e instanceof NullPointerException) {
                    log.error("Exception tag --> " + "Empty entry passed");
                } else if (e instanceof NumberFormatException) {
                    log.error("Exception tag --> "
                            + "Invalid number found in current id");
                } else if (e instanceof SQLException) {
                    log.error("Exception tag --> " + "Invalid sql statement "
                            + e.getMessage());
                }
                return null;
            } catch (Exception e) {
                log.error("Exception tag --> " + "Error");
                return null;
            }
        }
        return Mainlist;
    }

    public ArrayList<ArrayList<String>> getCurrency() {

        String LKR = null;
        String USD = null;

        ArrayList<ArrayList<String>> Mainlist
                = new ArrayList<ArrayList<String>>();

        if (star.con == null) {

            log.info(" Exception tag --> " + "Databse connection failiure. ");
            return null;

        } else {
            try {

                String query = "select *"
                        + "From currency ";

                PreparedStatement pstmt = star.con.prepareStatement(query);
                ResultSet r = pstmt.executeQuery();

                while (r.next()) {

                    ArrayList<String> list = new ArrayList<String>();

                    LKR = r.getString("lkr_rate");
                    USD = r.getString("usd_rate");

                    list.add(LKR);
                    list.add(USD);

                    Mainlist.add(list);

                }

            } catch (ArrayIndexOutOfBoundsException | SQLException |
                    NullPointerException e) {

                if (e instanceof ArrayIndexOutOfBoundsException) {

                    log.error("Exception tag --> "
                            + "Invalid entry location for list");

                } else if (e instanceof SQLException) {

                    log.error("Exception tag --> " + "Invalid sql statement"
                            + e.getMessage());

                } else if (e instanceof NullPointerException) {

                    log.error("Exception tag --> " + "Empty entry for list");

                }
                return null;
            } catch (Exception e) {

                log.error("Exception tag --> " + "Error");

                return null;
            }
        }
        return Mainlist;
    }

    public ArrayList<ArrayList<String>> getRate() {

        String nbt = null;
        String isNbt = null;
        String tdl = null;
        String isTdl = null;

        ArrayList<ArrayList<String>> Mainlist
                = new ArrayList<ArrayList<String>>();

        if (star.con == null) {

            log.info(" Exception tag --> " + "Databse connection failiure. ");
            return null;

        } else {
            try {

                String query = "select *"
                        + "From rate ";

                PreparedStatement pstmt = star.con.prepareStatement(query);
                ResultSet r = pstmt.executeQuery();

                while (r.next()) {

                    ArrayList<String> list = new ArrayList<String>();

                    nbt = r.getString("nbt");
                    isNbt = r.getString("is_nbt");
                    tdl = r.getString("tdl");
                    isTdl = r.getString("is_tdl");

                    list.add(nbt);
                    list.add(isNbt);
                    list.add(tdl);
                    list.add(isTdl);

                    Mainlist.add(list);

                }

            } catch (ArrayIndexOutOfBoundsException | SQLException |
                    NullPointerException e) {

                if (e instanceof ArrayIndexOutOfBoundsException) {

                    log.error("Exception tag --> "
                            + "Invalid entry location for list");

                } else if (e instanceof SQLException) {

                    log.error("Exception tag --> " + "Invalid sql statement"
                            + e.getMessage());

                } else if (e instanceof NullPointerException) {

                    log.error("Exception tag --> " + "Empty entry for list");

                }
                return null;
            } catch (Exception e) {

                log.error("Exception tag --> " + "Error");

                return null;
            }
        }
        return Mainlist;
    }

    public String getCustomerIdFromSummaryId(String summaryId) {

        String encodedSummaryId = ESAPI.encoder().encodeForSQL(ORACLE_CODEC,
                summaryId);
        String customerId = null;

        if (star.con == null) {
            log.error(" Exception tag --> " + "Databse connection failiure. ");
            return null;

        } else {
            try {

                String query
                        = "SELECT cus_id FROM res_summary where summary_no=? ";
                PreparedStatement pstmt = star.con.prepareStatement(query);
                pstmt.setString(1, encodedSummaryId);
                ResultSet r = pstmt.executeQuery();

                while (r.next()) {
                    customerId = r.getString("cus_id");

                }

            } catch (ArrayIndexOutOfBoundsException | SQLException |
                    NullPointerException e) {

                if (e instanceof ArrayIndexOutOfBoundsException) {
                    log.error("Exception tag --> "
                            + "Invalid entry location for list");
                } else if (e instanceof SQLException) {
                    log.error("Exception tag --> " + "Invalid sql statement");
                } else if (e instanceof NullPointerException) {
                    log.error("Exception tag --> " + "Empty entry for list");
                }
                return null;
            } catch (Exception e) {
                log.error("Exception tag --> " + "Error");
                return null;
            }
        }
        return customerId;

    }

    public String getSundryIdFromLaundryId(String laundryId) {

        String encodedLaundryId = ESAPI.encoder().encodeForSQL(ORACLE_CODEC,
                laundryId);
        String sundryId = null;

        if (star.con == null) {
            log.error(" Exception tag --> " + "Databse connection failiure. ");
            return null;

        } else {
            try {

                String query
                        = "SELECT sundry_bill_id FROM laundry_info where laundry_Bill_id=? ";
                PreparedStatement pstmt = star.con.prepareStatement(query);
                pstmt.setString(1, encodedLaundryId);
                ResultSet r = pstmt.executeQuery();

                while (r.next()) {
                    sundryId = r.getString("sundry_bill_id");

                }

            } catch (ArrayIndexOutOfBoundsException | SQLException |
                    NullPointerException e) {

                if (e instanceof ArrayIndexOutOfBoundsException) {
                    log.error("Exception tag --> "
                            + "Invalid entry location for list");
                } else if (e instanceof SQLException) {
                    log.error("Exception tag --> " + "Invalid sql statement");
                } else if (e instanceof NullPointerException) {
                    log.error("Exception tag --> " + "Empty entry for list");
                }
                return null;
            } catch (Exception e) {
                log.error("Exception tag --> " + "Error");
                return null;
            }
        }
        return sundryId;
    }

    public String getCustomerIdFromSundryId(String sundryId) {

        String encodedSummaryId = ESAPI.encoder().encodeForSQL(ORACLE_CODEC,
                sundryId);
        String customerId = null;

        if (star.con == null) {
            log.error(" Exception tag --> " + "Databse connection failiure. ");
            return null;

        } else {
            try {

                String query
                        = "SELECT cus_id FROM customer_outstanding_sundry where mb_id=? ";
                PreparedStatement pstmt = star.con.prepareStatement(query);
                pstmt.setString(1, encodedSummaryId);
                ResultSet r = pstmt.executeQuery();

                while (r.next()) {
                    customerId = r.getString("cus_id");

                }

            } catch (ArrayIndexOutOfBoundsException | SQLException |
                    NullPointerException e) {

                if (e instanceof ArrayIndexOutOfBoundsException) {
                    log.error("Exception tag --> "
                            + "Invalid entry location for list");
                } else if (e instanceof SQLException) {
                    log.error("Exception tag --> " + "Invalid sql statement");
                } else if (e instanceof NullPointerException) {
                    log.error("Exception tag --> " + "Empty entry for list");
                }
                return null;
            } catch (Exception e) {
                log.error("Exception tag --> " + "Error");
                return null;
            }
        }
        return customerId;

    }

    public ArrayList<String> loadRoomCategory() {

        String category = null;
        ArrayList list = new ArrayList();

        if (star.con == null) {
            log.error(" Exception tag --> " + "Databse connection failiure. ");
            return null;

        } else {
            try {

                String query = "SELECT * FROM room_category ";
                PreparedStatement pstmt = star.con.prepareStatement(query);
                ResultSet r = pstmt.executeQuery();

                while (r.next()) {
                    category = r.getString("category");
                    list.add(category);

                }

            } catch (ArrayIndexOutOfBoundsException | SQLException |
                    NullPointerException e) {

                if (e instanceof ArrayIndexOutOfBoundsException) {
                    log.error("Exception tag --> "
                            + "Invalid entry location for list");
                } else if (e instanceof SQLException) {
                    log.error("Exception tag --> " + "Invalid sql statement");
                } else if (e instanceof NullPointerException) {
                    log.error("Exception tag --> " + "Empty entry for list");
                }
                return null;
            } catch (Exception e) {
                log.error("Exception tag --> " + "Error");
                return null;
            }
        }
        return list;
    }

    public int getCategoryID(String category) {
        String encodedCategory = ESAPI.encoder().encodeForSQL(ORACLE_CODEC,
                category);

        int categoryId = 0;
        if (star.con == null) {
            log.info(" Exception tag --> " + "Databse connection failiure. ");
            return categoryId;
        } else {
            try {

                String query = "SELECT id from room_category where category= ? ";

                PreparedStatement pstmt = star.con.prepareStatement(query);
                pstmt.setString(1, encodedCategory);

                ResultSet r = pstmt.executeQuery();

                while (r.next()) {
                    categoryId = Integer.parseInt(r.getString("id"));
                }

            } catch (ArrayIndexOutOfBoundsException | SQLException |
                    NullPointerException e) {

                if (e instanceof ArrayIndexOutOfBoundsException) {

                    log.error(
                            "Exception tag --> "
                            + "Invalid entry location for list");

                } else if (e instanceof SQLException) {

                    log.error("Exception tag --> " + "Invalid sql statement");

                } else if (e instanceof NullPointerException) {

                    log.error("Exception tag --> " + "Empty entry for list");

                }
                return categoryId;
            } catch (Exception e) {

                log.error("Exception tag --> " + "Error");

                return categoryId;
            }
        }
        return categoryId;
    }

    public String getCategory(String roomNo) {

        String category = null;
        if (star.con == null) {
            log.info(" Exception tag --> " + "Databse connection failiure. ");
            return category;
        } else {
            try {

                String query = "SELECT r.room_category,r.rno,rc.* FROM "
                        + "room_reg r INNER JOIN room_category rc "
                        + "ON r.room_category = rc.id "
                        + "where r.rno= ? ";

                PreparedStatement pstmt = star.con.prepareStatement(query);
                pstmt.setString(1, roomNo);

                ResultSet r = pstmt.executeQuery();

                while (r.next()) {
                    category = r.getString("rc.category");
                }

            } catch (ArrayIndexOutOfBoundsException | SQLException |
                    NullPointerException e) {

                if (e instanceof ArrayIndexOutOfBoundsException) {

                    log.error(
                            "Exception tag --> "
                            + "Invalid entry location for list");

                } else if (e instanceof SQLException) {

                    log.error("Exception tag --> " + "Invalid sql statement");

                } else if (e instanceof NullPointerException) {

                    log.error("Exception tag --> " + "Empty entry for list");

                }
                return category;
            } catch (Exception e) {

                log.error("Exception tag --> " + "Error");

                return category;
            }
        }
        return category;
    }

    public int getRoomBasisID(String roomBasis) {
        String encodedBasis = ESAPI.encoder().encodeForSQL(ORACLE_CODEC,
                roomBasis);

        int roomBasisId = 0;
        if (star.con == null) {
            log.info(" Exception tag --> " + "Databse connection failiure. ");
            return roomBasisId;
        } else {
            try {

                String query
                        = "SELECT * from `room_basis` where room_basis = ? ";

                PreparedStatement pstmt = star.con.prepareStatement(query);
                pstmt.setString(1, encodedBasis);

                ResultSet r = pstmt.executeQuery();

                while (r.next()) {
                    roomBasisId = r.getInt("id");

                }

            } catch (ArrayIndexOutOfBoundsException | SQLException |
                    NullPointerException e) {

                if (e instanceof ArrayIndexOutOfBoundsException) {

                    log.error(
                            "Exception tag --> "
                            + "Invalid entry location for list");

                } else if (e instanceof SQLException) {

                    log.error("Exception tag --> " + "Invalid sql statement");

                } else if (e instanceof NullPointerException) {

                    log.error("Exception tag --> " + "Empty entry for list");

                }
                return roomBasisId;
            } catch (Exception e) {

                log.error("Exception tag --> " + "Error");

                return roomBasisId;
            }
        }
        return roomBasisId;
    }

    public String getRoomBasis(String roomId) {
        String encodedBasisId = ESAPI.encoder().encodeForSQL(ORACLE_CODEC,
                roomId);

        String roomBasis = null;
        if (star.con == null) {
            log.info(" Exception tag --> " + "Databse connection failiure. ");
            return roomBasis;
        } else {
            try {

                String query = "SELECT * from `room_basis` where id = ? ";

                PreparedStatement pstmt = star.con.prepareStatement(query);
                pstmt.setString(1, encodedBasisId);

                ResultSet r = pstmt.executeQuery();

                while (r.next()) {
                    roomBasis = r.getString("room_basis");

                }

            } catch (ArrayIndexOutOfBoundsException | SQLException |
                    NullPointerException e) {

                if (e instanceof ArrayIndexOutOfBoundsException) {

                    log.error(
                            "Exception tag --> "
                            + "Invalid entry location for list");

                } else if (e instanceof SQLException) {

                    log.error("Exception tag --> " + "Invalid sql statement");

                } else if (e instanceof NullPointerException) {

                    log.error("Exception tag --> " + "Empty entry for list");

                }
                return roomBasis;
            } catch (Exception e) {

                log.error("Exception tag --> " + "Error");

                return roomBasis;
            }
        }
        return roomBasis;
    }

    public int getLocationID(String locationName) {
        String encodedLocation = ESAPI.encoder().encodeForSQL(ORACLE_CODEC,
                locationName);

        int locationID = 0;
        if (star.con == null) {
            log.info(" Exception tag --> " + "Databse connection failiure. ");
            return locationID;
        } else {
            try {

                String query = "SELECT id from room_location where location= ? ";

                PreparedStatement pstmt = star.con.prepareStatement(query);
                pstmt.setString(1, encodedLocation);

                ResultSet r = pstmt.executeQuery();

                while (r.next()) {
                    locationID = Integer.parseInt(r.getString("id"));
                }

            } catch (ArrayIndexOutOfBoundsException | SQLException |
                    NullPointerException e) {

                if (e instanceof ArrayIndexOutOfBoundsException) {

                    log.error(
                            "Exception tag --> "
                            + "Invalid entry location for list");

                } else if (e instanceof SQLException) {

                    log.error("Exception tag --> " + "Invalid sql statement");

                } else if (e instanceof NullPointerException) {

                    log.error("Exception tag --> " + "Empty entry for list");

                }
                return locationID;
            } catch (Exception e) {

                log.error("Exception tag --> " + "Error");

                return locationID;
            }
        }
        return locationID;
    }

    public int getMsgTypeId(String msgType) {
        String encodedMsgType = ESAPI.encoder().encodeForSQL(ORACLE_CODEC,
                msgType);

        int msgTypeId = 0;
        if (star.con == null) {
            log.info(" Exception tag --> " + "Databse connection failiure. ");
            return msgTypeId;
        } else {
            try {

                String query = "SELECT * FROM msg_type WHERE msg_type = ? ";

                PreparedStatement pstmt = star.con.prepareStatement(query);
                pstmt.setString(1, encodedMsgType);

                ResultSet r = pstmt.executeQuery();

                while (r.next()) {
                    msgTypeId = Integer.parseInt(r.getString("id"));
                }

            } catch (ArrayIndexOutOfBoundsException | SQLException |
                    NullPointerException e) {

                if (e instanceof ArrayIndexOutOfBoundsException) {

                    log.error(
                            "Exception tag --> "
                            + "Invalid entry location for list");

                } else if (e instanceof SQLException) {

                    log.error("Exception tag --> " + "Invalid sql statement");

                } else if (e instanceof NullPointerException) {

                    log.error("Exception tag --> " + "Empty entry for list");

                }
                return msgTypeId;
            } catch (Exception e) {

                log.error("Exception tag --> " + "Error");

                return msgTypeId;
            }
        }
        return msgTypeId;
    }

    public int getLanguageId(String language) {
        String encodedLanguage = ESAPI.encoder().encodeForSQL(ORACLE_CODEC,
                language);

        int languageId = 0;
        if (star.con == null) {
            log.info(" Exception tag --> " + "Databse connection failiure. ");
            return languageId;
        } else {
            try {

                String query = "SELECT * FROM msg_language WHERE title = ? ";

                PreparedStatement pstmt = star.con.prepareStatement(query);
                pstmt.setString(1, encodedLanguage);

                ResultSet r = pstmt.executeQuery();

                while (r.next()) {
                    languageId = Integer.parseInt(r.getString("id"));
                }

            } catch (ArrayIndexOutOfBoundsException | SQLException |
                    NullPointerException e) {

                if (e instanceof ArrayIndexOutOfBoundsException) {

                    log.error(
                            "Exception tag --> "
                            + "Invalid entry location for list");

                } else if (e instanceof SQLException) {

                    log.error("Exception tag --> " + "Invalid sql statement");

                } else if (e instanceof NullPointerException) {

                    log.error("Exception tag --> " + "Empty entry for list");

                }
                return languageId;
            } catch (Exception e) {

                log.error("Exception tag --> " + "Error");

                return languageId;
            }
        }
        return languageId;
    }

    public String getLocation(int id) {

        String location = null;
        if (star.con == null) {
            log.info(" Exception tag --> " + "Databse connection failiure. ");
            return location;
        } else {
            try {

                String query = "SELECT location from room_location where id= ? ";

                PreparedStatement pstmt = star.con.prepareStatement(query);
                pstmt.setInt(1, id);

                ResultSet r = pstmt.executeQuery();

                while (r.next()) {
                    location = r.getString("location");
                }

            } catch (ArrayIndexOutOfBoundsException | SQLException |
                    NullPointerException e) {

                if (e instanceof ArrayIndexOutOfBoundsException) {

                    log.error(
                            "Exception tag --> "
                            + "Invalid entry location for list");

                } else if (e instanceof SQLException) {

                    log.error("Exception tag --> " + "Invalid sql statement");

                } else if (e instanceof NullPointerException) {

                    log.error("Exception tag --> " + "Empty entry for list");

                }
                return location;
            } catch (Exception e) {

                log.error("Exception tag --> " + "Error");

                return location;
            }
        }
        return location;
    }

    public String getMarket(int id) {

        String market = null;
        if (star.con == null) {
            log.info(" Exception tag --> " + "Databse connection failiure. ");
            return market;
        } else {
            try {

                String query = "SELECT market_type from market where id= ? ";

                PreparedStatement pstmt = star.con.prepareStatement(query);
                pstmt.setInt(1, id);

                ResultSet r = pstmt.executeQuery();

                while (r.next()) {
                    market = r.getString("market_type");
                }

            } catch (ArrayIndexOutOfBoundsException | SQLException |
                    NullPointerException e) {

                if (e instanceof ArrayIndexOutOfBoundsException) {

                    log.error(
                            "Exception tag --> "
                            + "Invalid entry location for list");

                } else if (e instanceof SQLException) {

                    log.error("Exception tag --> " + "Invalid sql statement");

                } else if (e instanceof NullPointerException) {

                    log.error("Exception tag --> " + "Empty entry for list");

                }
                return market;
            } catch (Exception e) {

                log.error("Exception tag --> " + "Error");

                return market;
            }
        }
        return market;
    }

    public ArrayList<String> getReservationProfile(
            String reservationProfileId) {

        String encodedId = ESAPI.encoder().encodeForSQL(ORACLE_CODEC,
                reservationProfileId);
        ArrayList<String> list = new ArrayList();

        String startTime = null;
        String endTime = null;
        String checkInTime = null;
        String checkOutTime = null;

        if (star.con == null) {

            log.error("Exception tag --> " + "Databse connection failiure. ");
            return null;
        } else {
            try {

                String query
                        = "SELECT  * "
                        + "FROM res_duration rd INNER JOIN "
                        + "res_type rt ON "
                        + "rd.accommodation_type = rt.id "
                        + "WHERE rd.profile_id = ? ";

                PreparedStatement pstmt = star.con.prepareStatement(query);
                pstmt.setString(1, encodedId);

                ResultSet r = pstmt.executeQuery();

                while (r.next()) {

                    startTime = r.getString("rd.start_time");
                    endTime = r.getString("rd.end_time");
                    checkInTime = r.getString("rd.early_check_in");
                    checkOutTime = r.getString("rd.late_checkout");

                    list.add(startTime);
                    list.add(endTime);
                    list.add(checkInTime);
                    list.add(checkOutTime);

                }

            } catch (ArrayIndexOutOfBoundsException | SQLException |
                    NullPointerException e) {

                if (e instanceof ArrayIndexOutOfBoundsException) {

                    log.error("Exception tag --> "
                            + "Invalid entry location for list");

                } else if (e instanceof SQLException) {

                    log.error("Exception tag --> " + "Invalid sql statement");
                    e.printStackTrace();

                } else if (e instanceof NullPointerException) {

                    log.error("Exception tag --> " + "Empty entry for list");

                }
                return null;
            } catch (Exception e) {

                log.error("Exception tag --> " + "Error");

                return null;
            }

        }
        return list;
    }

    public ArrayList<ArrayList<String>> searchRoomDetails(String date) {

        String encodedDate = ESAPI.encoder().encodeForSQL(ORACLE_CODEC, date);

        String rno = null;
        String cusName = null;
        String summeryId = null;

        ArrayList<ArrayList<String>> Mainlist
                = new ArrayList<ArrayList<String>>();

        if (star.con == null) {

            log.info(" Exception tag --> " + "Databse connection failiure. ");
            return null;

        } else {
            try {

                String query = "SELECT r.room_no,c.cus_name,r.summary_no "
                        + "FROM res_summary r "
                        + "inner join group_cus gc "
                        + "on gc.summary_id = r.summary_no "
                        + "inner join customer c "
                        + "on c.cus_id=gc.customer_id "
                        + "where r.no_show =0 and r.reg_no LIKE 'REG%' and ? BETWEEN r.a_date AND "
                        + "r.d_date AND r.is_masterbill =0 AND r.status=1 ";

                PreparedStatement pstmt = star.con.prepareStatement(query);
                pstmt.setString(1, encodedDate);
                ResultSet r = pstmt.executeQuery();

                while (r.next()) {

                    ArrayList<String> list = new ArrayList<String>();

                    rno = r.getString("r.room_no");
                    cusName = r.getString("c.cus_name");
                    summeryId = r.getString("r.summary_no");

                    list.add(rno);
                    list.add(cusName);
                    list.add(summeryId);

                    Mainlist.add(list);

                }

            } catch (ArrayIndexOutOfBoundsException | SQLException |
                    NullPointerException e) {

                if (e instanceof ArrayIndexOutOfBoundsException) {

                    log.error("Exception tag --> "
                            + "Invalid entry location for list");

                } else if (e instanceof SQLException) {

                    log.error("Exception tag --> " + "Invalid sql statement");

                } else if (e instanceof NullPointerException) {

                    log.error("Exception tag --> " + "Empty entry for list");

                }
                return null;
            } catch (Exception e) {

                log.error("Exception tag --> " + "Error");

                return null;
            }
        }
        return Mainlist;
    }

    public ArrayList<ArrayList<String>> searchRoomDetailsInfo(String date) {

        String encodedDate = ESAPI.encoder().encodeForSQL(ORACLE_CODEC, date);

        String rno = null;
        String cusName = null;
        String summeryId = null;
        String cusId = null;
        String pax = null;

        ArrayList<ArrayList<String>> Mainlist
                = new ArrayList<ArrayList<String>>();

        if (star.con == null) {

            log.info(" Exception tag --> " + "Databse connection failiure. ");
            return null;

        } else {
            try {

                String query
                        = "SELECT r.room_no as Room_No ,gc.customer_id as Id ,c.cus_name as Name,r.summary_no as Summary_Id , "
                        + "ifnull(adults,0) + ifnull(children,0) as pax  "
                        + "FROM res_summary r  "
                        + "inner join group_cus gc  "
                        + "on r.summary_no=gc.summary_id  "
                        + "inner join res_summary_basis_cus bc  "
                        + "on r.summary_no=bc.summary_id  "
                        + "inner join customer c  "
                        + "on c.cus_id=gc.customer_id  "
                        + "where r.no_show =0 and r.reg_no LIKE 'REG%' and bc.date = ? AND is_masterbill = '0' "
                        + "union "
                        + "SELECT r.room_no as Room_No ,gc.customer_id as Id ,c.cus_name as Name,r.summary_no as Summary_Id , "
                        + "ifnull(adults,0) + ifnull(children,0) as pax  "
                        + "FROM res_summary r  "
                        + "inner join group_cus gc  "
                        + "on r.summary_no=gc.summary_id  "
                        + "inner join res_summary_basis_group bc  "
                        + "on r.summary_no=bc.summary_id  "
                        + "inner join customer c  "
                        + "on c.cus_id=gc.customer_id  "
                        + "where r.no_show =0 and r.reg_no LIKE 'REG%' and bc.date = ? AND is_masterbill = '0'";

                PreparedStatement pstmt = star.con.prepareStatement(query);
                pstmt.setString(1, encodedDate);
                pstmt.setString(2, encodedDate);
                ResultSet r = pstmt.executeQuery();

                while (r.next()) {

                    ArrayList<String> list = new ArrayList<String>();

                    rno = r.getString("Room_No");
                    cusName = r.getString("Name");
                    cusId = r.getString("Id");
                    summeryId = r.getString("Summary_Id");
                    pax = r.getString("pax");

                    list.add(rno);
                    list.add(summeryId);
                    list.add(cusName);
                    list.add(cusId);
                    list.add(pax);

                    Mainlist.add(list);

                }

            } catch (ArrayIndexOutOfBoundsException | SQLException |
                    NullPointerException e) {

                if (e instanceof ArrayIndexOutOfBoundsException) {

                    log.error("Exception tag --> "
                            + "Invalid entry location for list");

                } else if (e instanceof SQLException) {

                    log.error("Exception tag --> " + "Invalid sql statement");

                } else if (e instanceof NullPointerException) {

                    log.error("Exception tag --> " + "Empty entry for list");

                }
                return null;
            } catch (Exception e) {

                log.error("Exception tag --> " + "Error");

                return null;
            }
        }
        return Mainlist;
    }

    //From BotDAO
    public boolean isExtraOrderAvailable(String cus_id, String eventId) {

        boolean dataAvailability = false;
        String encodedCusId = ESAPI.encoder().encodeForSQL(ORACLE_CODEC, cus_id);
        String encodedEventId = ESAPI.encoder().encodeForSQL(ORACLE_CODEC,
                eventId);

        if (star.con == null) {
            log.error("Databse connection failiure.");
            return false;
        } else {
            try {

                String query = "SELECT * FROM bnq_foodmenuselection "
                        + "WHERE cus_id=? AND event_name = ? ";
                PreparedStatement pstmt = star.con.prepareStatement(query);
                pstmt.setString(1, encodedCusId);
                pstmt.setString(2, encodedEventId);

                ResultSet r = pstmt.executeQuery();
                while (r.next()) {

                    dataAvailability = true;

                }
            } catch (NullPointerException | NumberFormatException | SQLException e) {

                if (e instanceof NullPointerException) {
                    log.error("Exception tag --> " + "Empty entry passed");
                } else if (e instanceof NumberFormatException) {
                    log.error("Exception tag --> "
                            + "Invalid number found in current id");
                } else if (e instanceof SQLException) {
                    log.error("Exception tag --> " + "Invalid sql statement "
                            + e.getMessage());
                }
                return false;
            } catch (Exception e) {
                log.error("Exception tag --> " + "Error");
                return false;
            }
        }
        return dataAvailability;
    }

    public ArrayList<String> loadCustomerInfo(String cus_id, String event_id) {
        String encodedCusId = ESAPI.encoder().encodeForSQL(ORACLE_CODEC, cus_id);
        String encodedEventId = ESAPI.encoder().encodeForSQL(ORACLE_CODEC,
                event_id);

        String discription = null;
        String pax_count = null;
        String date = null;

        ArrayList<String> list = new ArrayList<>();

        if (star.con == null) {
            log.error("Databse connection failiure.");

        } else {

            try {
                String query
                        = "SELECT * FROM event_log WHERE cus_id =? AND event_id = ?";
                PreparedStatement pstmt = star.con.prepareStatement(query);
                pstmt.setString(1, encodedCusId);
                pstmt.setString(2, encodedEventId);

                ResultSet r = pstmt.executeQuery();

                while (r.next()) {

                    discription = r.getString("event_log_event_name");
                    pax_count = r.getString("event_log_pax_count");
                    date = r.getString("event_log_date");

                    list.add(discription);
                    list.add(pax_count);
                    list.add(date);

                }
            } catch (NullPointerException | SQLException e) {

                if (e instanceof NullPointerException) {

                    log.error("Exception tag --> " + "Empty entry passed");

                } else if (e instanceof SQLException) {

                    log.error("Exception tag --> " + "Invalid sql statement "
                            + e.getMessage());

                }
                return null;
            } catch (Exception e) {

                log.error("Exception tag --> " + "Error");

                return null;
            }
        }
        return list;
    }
    /////////////////////////////////////////

    public boolean isNetworkReport(String title) {
        String encodedTitle = ESAPI.encoder().encodeForSQL(ORACLE_CODEC, title);
        boolean available = false;
        boolean isNetwork = false;
        String printType = null;

        if (star.con == null) {
            log.error("Databse connection failiure.");
            return false;
        } else {

            try {
                String query
                        = "SELECT is_Network FROM report where name=? ";
                PreparedStatement pstmt = star.con.prepareStatement(query);
                pstmt.setString(1, encodedTitle);

                ResultSet r = pstmt.executeQuery();

                while (r.next()) {
                    available = true;
                    printType = r.getString("is_Network");
                }

                if (available == true) {
                    if (printType.equals("1")) {
                        isNetwork = true;
                    } else {
                        isNetwork = false;
                    }
                }
            } catch (NullPointerException | SQLException e) {

                if (e instanceof NullPointerException) {
                    log.error("Exception tag --> " + "Empty entry passed");
                } else if (e instanceof SQLException) {
                    log.error("Exception tag --> " + "Invalid sql statement "
                            + e.getMessage());
                }
                return false;
            } catch (Exception e) {
                log.error("Exception tag --> " + "Error");
                return false;
            }
        }
        return isNetwork;
    }
}
