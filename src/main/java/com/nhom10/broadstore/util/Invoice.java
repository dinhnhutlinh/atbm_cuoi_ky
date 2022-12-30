package com.nhom10.broadstore.util;

import com.nhom10.broadstore.beans.Order;
import com.nhom10.broadstore.services.OrderServices;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType0Font;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Invoice {
    PDDocument invc;
    int n;
    Integer total = 0;
    Integer price;
    String custName;
    String custPh;
    List<String> productName = new ArrayList<String>();
    List<Integer> productPrice = new ArrayList<Integer>();
    List<Integer> productQty = new ArrayList<Integer>();
    String invoiceTitle = new String("BoardStore Invoice");
    String subTitle = new String("Invoice");
    PDFont font;

    //Using the constructor to create a PDF with a blank page
    public Invoice() throws IOException {
        //Create Document
        invc = new PDDocument();
        //Create Blank Page
        PDPage newpage = new PDPage();
        //Add the blank page
        invc.addPage(newpage);
        font = PDType0Font.load(invc, new File("times.ttf"));
    }

    public static void main(String args[]) throws IOException {
        // lấy order từ database
        // tạo invoice
        // gọi hàm getData
        // gọi hàm writeInvoice

        Invoice i = new Invoice();
        OrderServices orderServices = new OrderServices();
        Order order = orderServices.findById("qmfvdsympt");
        i.getdata(order);
        i.writeInvoice("invoice.pdf");
        System.out.println("Invoice Generated!");
    }

    void getdata(Order order) {

        System.out.println("Enter the Customer Name: ");
        custName = order.getName();
        System.out.println("Enter the Customer Phone Number: ");
        custPh = order.getPhone();
        System.out.println("Enter the Number of Products: ");
        n = order.getOrderItems().size();
        System.out.println();
        for (int i = 0; i < n; i++) {

            productName.add(order.getOrderItems().get(i).getProduct().getName());

            productPrice.add((int) order.getOrderItems().get(i).getProduct().getPrice());

            productQty.add(order.getOrderItems().get(i).getQuantity());
            //Calculating the total amount
            total = total + (productPrice.get(i) * productQty.get(i));
        }
    }

    void writeInvoice(String filePath) {
        //get the page
        PDPage mypage = invc.getPage(0);
        try {
            //Prepare Content Stream
            PDPageContentStream cs = new PDPageContentStream(invc, mypage);

            //Writing Single Line text
            //Writing the Invoice title
            cs.beginText();
            cs.setFont(font, 20);
            cs.newLineAtOffset(140, 750);
            cs.showText(invoiceTitle);
            cs.endText();

            cs.beginText();
            cs.setFont(font, 18);
            cs.newLineAtOffset(270, 690);
            cs.showText(subTitle);
            cs.endText();

            //Writing Multiple Lines
            //writing the customer details
            cs.beginText();
            cs.setFont(font, 14);
            cs.setLeading(20f);
            cs.newLineAtOffset(60, 610);
            cs.showText("Customer Name: ");
            cs.newLine();
            cs.showText("Phone Number: ");
            cs.endText();

            cs.beginText();
            cs.setFont(font, 14);
            cs.setLeading(20f);
            cs.newLineAtOffset(170, 610);
            cs.showText(custName);
            cs.newLine();
            cs.showText(custPh);
            cs.endText();

            cs.beginText();
            cs.setFont(font, 14);
            cs.newLineAtOffset(80, 540);
            cs.showText("Product Name");
            cs.endText();

            cs.beginText();
            cs.setFont(font, 14);
            cs.newLineAtOffset(200, 540);
            cs.showText("Unit Price");
            cs.endText();

            cs.beginText();
            cs.setFont(font, 14);
            cs.newLineAtOffset(310, 540);
            cs.showText("Quantity");
            cs.endText();

            cs.beginText();
            cs.setFont(font, 14);
            cs.newLineAtOffset(410, 540);
            cs.showText("Price");
            cs.endText();

            cs.beginText();
            cs.setFont(font, 12);
            cs.setLeading(20f);
            cs.newLineAtOffset(80, 520);
            for (int i = 0; i < n; i++) {
                cs.showText(productName.get(i));
                cs.newLine();
            }
            cs.endText();

            cs.beginText();
            cs.setFont(font, 12);
            cs.setLeading(20f);
            cs.newLineAtOffset(200, 520);
            for (int i = 0; i < n; i++) {
                cs.showText(productPrice.get(i).toString());
                cs.newLine();
            }
            cs.endText();

            cs.beginText();
            cs.setFont(font, 12);
            cs.setLeading(20f);
            cs.newLineAtOffset(310, 520);
            for (int i = 0; i < n; i++) {
                cs.showText(productQty.get(i).toString());
                cs.newLine();
            }
            cs.endText();

            cs.beginText();
            cs.setFont(font, 12);
            cs.setLeading(20f);
            cs.newLineAtOffset(410, 520);
            for (int i = 0; i < n; i++) {
                price = productPrice.get(i) * productQty.get(i);
                cs.showText(price.toString());
                cs.newLine();
            }
            cs.endText();

            cs.beginText();
            cs.setFont(font, 14);
            cs.newLineAtOffset(310, (500 - (20 * n)));
            cs.showText("Total: ");
            cs.endText();

            cs.beginText();
            cs.setFont(font, 14);
            //Calculating where total is to be written using number of products
            cs.newLineAtOffset(410, (500 - (20 * n)));
            cs.showText(total.toString());
            cs.endText();

            //Close the content stream
            cs.close();
            //Save the PDF
            invc.save(filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}