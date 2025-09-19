package org.example.drivenow_carrental_aad.service;

import org.example.drivenow_carrental_aad.dto.RentDetailsDto;
import org.example.drivenow_carrental_aad.service.impl.RentDetailServiceImpl;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReportService {

     private final RentDetailServiceImpl rentDetailService;

     public ReportService(RentDetailServiceImpl rentDetailService) {
         this.rentDetailService = rentDetailService;
     }

     public void generateDailyBookingReport(String outPath)throws JRException{
         List<RentDetailsDto> bookings = rentDetailService.getAllBookings();
         JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(bookings);
         Map<String, Object> parameters = new HashMap<>();
         parameters.put("ReportTitle", "Daily Booking Report");

         JasperReport jasperReport = JasperCompileManager.compileReport("src/main/resources/reports/booking_report.jrxml");
         JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
         JasperExportManager.exportReportToPdfFile(jasperPrint, outputPath);
     }
}
