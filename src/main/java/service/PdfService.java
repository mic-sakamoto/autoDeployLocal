package service;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

import action.PdfAction;
import model.PdfModel;
import net.sf.jasperreports.engine.JRPrintPage;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import utils.log4j.LogUtil;

public class PdfService extends ActionSupport {

    public static PdfAction as;

    public boolean init(PdfModel model) throws Exception {

        return true;

    }

    /*
     * ダウンロード処理
     * modelを引数で渡す
     */
    public boolean downloadPdf(PdfModel model) {
        boolean result = true;

        try {
            String pdfPath = ServletActionContext.getServletContext().getRealPath("") + "report/pdf/" + model.getPdfName() + ".pdf";
            LogUtil.info("pdfPath " + pdfPath);
            File file = new File(pdfPath);

            byte[] fileBytes;
            try (FileInputStream fis = new FileInputStream(file); ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = fis.read(buffer)) != -1) {
                    baos.write(buffer, 0, bytesRead);
                }
                fileBytes = baos.toByteArray();
            }

            model.setInputStream(new ByteArrayInputStream(fileBytes));

            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
            model.setFileName(sdf.format(calendar.getTime()) + "_" + model.getPdfName() + ".pdf");

            model.setContentLength(fileBytes.length);

        } catch (FileNotFoundException e) {
            result = false;
        } catch (Exception e) {
            result = false;
        }

        return result;
    }

    /*
     * 印刷処理
     * modelを引数で渡す
     */

    // データを変数のpdfもしくは変数のpdfsに追加する
    public boolean printJasper(PdfModel model) {
        boolean result = true;

        JasperPrint pdf = new JasperPrint();
        ArrayList<JasperPrint> pdfs = new ArrayList<JasperPrint>();
        NumberFormat nfNum = NumberFormat.getNumberInstance();

        try {

            if ("KeiyakuIchiran".equals(model.getPdfName())) {
                String jasperReportPath = ServletActionContext.getServletContext().getRealPath("") + "report/" + "KeiyakuIchiran.jasper";
                List<Map<String, Object>> list = new ArrayList<>();
                for (int i = 1; i <= 10; i++) {
                    HashMap<String, Object> rmap = new HashMap<>();

                    rmap.put("moshikomibi", "2024 / 08 / 01");
                    rmap.put("webuketsukeid", "2408010001001");
                    rmap.put("kokyakumei", "田中 太郎");
                    rmap.put("sutetasu", "審査申込条件可決");
                    rmap.put("hommoshikomibi", "2024 / 08 / 01");
                    rmap.put("berikanryobi", "2024 / 08 / 01");
                    rmap.put("tatekaebi", "2024 / 08 / 01");
                    rmap.put("nyuryokuhoho", "加盟店");
                    rmap.put("kameitenid", "123456789");
                    rmap.put("kameitemmei", "ユー・エス・エス東京");
                    rmap.put("tantoshamei", "担当 試験");
                    rmap.put("kokyakuid", "U123456");
                    rmap.put("daiko", "●");
                    rmap.put("koshimbi", "2024 / 08 / 01");

                    list.add(rmap);
                }

                HashMap<String, Object> params = new HashMap<>();
                params.put("chohyobi", "2024 / 08 / 01");

                pdf = createJasper(jasperReportPath, list, params);

            } else if ("LoneTorokuIraisho".equals(model.getPdfName())) {

                String jasperReportPath = ServletActionContext.getServletContext().getRealPath("") + "report/" + "LoneTorokuIraisho.jasper";

                List<Map<String, Object>> list = new ArrayList<>();

                HashMap<String, Object> rmap = new HashMap<>();
                rmap.put("kameitemmei", "株式会社あいうえおかきくけこ");
                rmap.put("hakkonen", "2023");
                rmap.put("hakkozuki", "12");
                rmap.put("hakkobi", "12");
                rmap.put("saikembango", "123456789");
                rmap.put("keiyakushokana", "タナカ　タロウ");
                rmap.put("keiyakushokanji", "田中　太郎");
                rmap.put("shamei", "エスティマ");
                list.add(rmap);

                HashMap<String, Object> params = new HashMap<>();
                String imgPath = ServletActionContext.getServletContext().getRealPath("") + "report/" + "img/LoneTorokuIraisho.png";
                InputStream img = new BufferedInputStream(new FileInputStream(new File(imgPath)));
                params.put("LoneTorokuIraisho", img);

                pdf = createJasper(jasperReportPath, list, params);

            } else if ("MCCSAutoLone".equals(model.getPdfName())) {

                String jasperReportPath1 = ServletActionContext.getServletContext().getRealPath("") + "report/" + "MCCSAutoLone1p.jasper";
                String jasperReportPath2 = ServletActionContext.getServletContext().getRealPath("") + "report/" + "MCCSAutoLone2p.jasper";
                String jasperReportPath3 = ServletActionContext.getServletContext().getRealPath("") + "report/" + "MCCSAutoLone3p.jasper";
                String jasperReportPath4 = ServletActionContext.getServletContext().getRealPath("") + "report/" + "MCCSAutoLone4p.jasper";

                List<Map<String, Object>> list = new ArrayList<>();
                HashMap<String, Object> rmap = new HashMap<>();
                rmap.put("hommoshikominen", "2024");
                rmap.put("hommoshikomizuki", "12");
                rmap.put("hommoshikomibi", "11");
                rmap.put("shomeisain", "山田 太郎");
                list.add(rmap);

                HashMap<String, Object> params1 = new HashMap<>();
                String imgPath1 = ServletActionContext.getServletContext().getRealPath("") + "report/" + "img/MCCSAutoLone1p.png";
                InputStream img1 = new BufferedInputStream(new FileInputStream(new File(imgPath1)));
                params1.put("MCCSAutoLone1p", img1);

                HashMap<String, Object> params2 = new HashMap<>();
                String imgPath2 = ServletActionContext.getServletContext().getRealPath("") + "report/" + "img/MCCSAutoLone2p.png";
                InputStream img2 = new BufferedInputStream(new FileInputStream(new File(imgPath2)));
                params2.put("MCCSAutoLone2p", img2);

                HashMap<String, Object> params3 = new HashMap<>();
                String imgPath3 = ServletActionContext.getServletContext().getRealPath("") + "report/" + "img/MCCSAutoLone3p.png";
                InputStream img3 = new BufferedInputStream(new FileInputStream(new File(imgPath3)));
                params3.put("MCCSAutoLone3p", img3);

                HashMap<String, Object> params4 = new HashMap<>();
                String imgPath4 = ServletActionContext.getServletContext().getRealPath("") + "report/" + "img/MCCSAutoLone4p.png";
                InputStream img4 = new BufferedInputStream(new FileInputStream(new File(imgPath4)));
                params4.put("MCCSAutoLone4p", img4);

                JasperPrint pdf1 = new JasperPrint();
                JasperPrint pdf2 = new JasperPrint();
                JasperPrint pdf3 = new JasperPrint();
                JasperPrint pdf4 = new JasperPrint();
                pdf1 = createJasper(jasperReportPath1, list, params1);

                pdf2 = createJasper(jasperReportPath2, list, params2);

                pdf3 = createJasper(jasperReportPath3, list, params3);

                pdf4 = createJasper(jasperReportPath4, list, params4);

                pdfs.add(pdf1);
                pdfs.add(pdf2);
                pdfs.add(pdf3);
                pdfs.add(pdf4);

                pdf = combinePdf(pdfs);

            }else if ("SeisanMeisaisho".equals(model.getPdfName())) {

                String jasperReportPath1 = ServletActionContext.getServletContext().getRealPath("") + "report/" + "ShiharaiTsuchisho.jasper";
                String jasperReportPath2 = ServletActionContext.getServletContext().getRealPath("") + "report/" + "SeisanMeisaishoShisha.jasper";
                String jasperReportPath3 = ServletActionContext.getServletContext().getRealPath("") + "report/" + "SeisanMeisaishoKokyaku.jasper";

                List<Map<String, Object>> list1 = new ArrayList<>();

                HashMap<String, Object> rmap1 = new HashMap<>();
                rmap1.put("shutsuryokunen", "2023");
                rmap1.put("shutsuryokuzuki", "11");
                rmap1.put("shutsuryokubi", "6");
                rmap1.put("yubimbango", "〒710-0043");
                rmap1.put("jusho", "岡山県倉敷市羽島２０６－４");
                rmap1.put("kaishamei", "株式会社japan DHAホールディングス　様");
                rmap1.put("shiharaikodo", "109800001");
                rmap1.put("shiharaishimekirinen", "2023");
                rmap1.put("shiharaishimekirizuki", "11");
                rmap1.put("shiharaishimekiribi", "4");
                rmap1.put("shiharaiyoteinen", "2023");
                rmap1.put("shiharaiyoteizuki", "11");
                rmap1.put("shiharaiyoteibi", "7");
                rmap1.put("ginkomei", "フクオカギンコウ");
                rmap1.put("shitemmei", "カスガシテン");
                rmap1.put("koza", "普通 0147551");
                rmap1.put("kozameigi", "ジャパンホールディングス");
                rmap1.put("kensu", "6");
                rmap1.put("tatekaekin", nfNum.format(999999900));
                rmap1.put("kameitentesuryo", nfNum.format(9999));
                rmap1.put("fuzumihoshokin", nfNum.format(9999));
                rmap1.put("kojokomokuichi", "-1");
                rmap1.put("kojokomokuni", nfNum.format(9999));
                rmap1.put("kojokomokusan", nfNum.format(9999));
                rmap1.put("kojokomokushi", nfNum.format(9999));
                rmap1.put("kojokomokugo", nfNum.format(9999));
                rmap1.put("kyanserutatekaekin", nfNum.format(-4000000));
                rmap1.put("sashihikishiharaikin", nfNum.format(999582300));
                rmap1.put("biko", "100570001 MCCS費用264,000円");

                list1.add(rmap1);
                
                List<Map<String, Object>> list2 = new ArrayList<>();
                for (int i = 1; i <= 24; i++) {
                    HashMap<String, Object> rmap2 = new HashMap<>();

                    rmap2.put("kameiten", "株式会社japan DHAホールディングス");
                    rmap2.put("kensu", "6");
                    rmap2.put("tatekaekin", nfNum.format(999999900));
                    rmap2.put("kameitentesuryo", nfNum.format(9999));
                    rmap2.put("fuzumihoshokin", nfNum.format(9999));
                    rmap2.put("kojokomokuichi", nfNum.format(9999));
                    rmap2.put("kojokomokuni", nfNum.format(-9999));
                    rmap2.put("kojokomokusan", nfNum.format(-9999));
                    rmap2.put("kojokomokushi", nfNum.format(-9999));
                    rmap2.put("kojokomokugo", nfNum.format(9999));
                    rmap2.put("kyanserutatekaekin", nfNum.format(-4000000));
                    rmap2.put("sashihikigaku", nfNum.format(999582300));

                    list2.add(rmap2);
                }
                
                List<Map<String, Object>> list3 = new ArrayList<>();
                for (int i = 1; i <= 24; i++) {
                    HashMap<String, Object> rmap3 = new HashMap<>();

                    rmap3.put("keiyakusha", "株式会社japan DHAホールディングス");
                    rmap3.put("kensu", "6");
                    rmap3.put("tatekaekin", nfNum.format(999999900));
                    rmap3.put("kojokomokuichi", nfNum.format(9999));
                    rmap3.put("kyanserutatekaekin", nfNum.format(-4000000));
                    rmap3.put("sashihikigaku", nfNum.format(999582300));

                    list3.add(rmap3);
                }

                HashMap<String, Object> params1 = new HashMap<>();
                String imgPath = ServletActionContext.getServletContext().getRealPath("") + "report/" + "img/ShiharaiTsuchisho.png";
                InputStream img = new BufferedInputStream(new FileInputStream(new File(imgPath)));
                params1.put("ShiharaiTsuchisho", img);
                
                HashMap<String, Object> params2 = new HashMap<>();
                params2.put("yubimbango", "〒710-0043");
                params2.put("jusho", "岡山県倉敷市羽島２０６－４");
                params2.put("kaishamei", "株式会社japan DHAホールディングス 様");
                params2.put("kameitenid", "123456");
                params2.put("meisaishomei", "支払通知書");
                params2.put("shimekirinen", "2023");
                params2.put("shimekirizuki", "11");
                params2.put("shimekiribi", "4");
                params2.put("shiharaiyoteinen", "2023");
                params2.put("shiharaiyoteizuki", "11");
                params2.put("shiharaiyoteibi", "7");
                
                HashMap<String, Object> params3 = new HashMap<>();
                params3.put("yubimbango", "〒710-0043");
                params3.put("jusho", "岡山県倉敷市羽島２０６－４");
                params3.put("kaishamei", "株式会社japan DHAホールディングス 様");
                params3.put("kameitenid", "123456");
                params3.put("meisaishomei", "支払通知書");
                params3.put("shimekirinen", "2023");
                params3.put("shimekirizuki", "11");
                params3.put("shimekiribi", "4");
                params3.put("shiharaiyoteinen", "2023");
                params3.put("shiharaiyoteizuki", "11");
                params3.put("shiharaiyoteibi", "7");

                JasperPrint pdf1 = new JasperPrint();
                JasperPrint pdf2 = new JasperPrint();
                JasperPrint pdf3 = new JasperPrint();

                pdf1 = createJasper(jasperReportPath1, list1, params1);

                pdf2 = createJasper(jasperReportPath2, list2, params2);

                pdf3 = createJasper(jasperReportPath3, list3, params3);

                pdfs.add(pdf1);
                pdfs.add(pdf2);
                pdfs.add(pdf3);

                pdf = combinePdf(pdfs);

            }  else if ("ShiharaiYoteihyo".equals(model.getPdfName())) {

                String jasperReportPath = ServletActionContext.getServletContext().getRealPath("") + "report/" + "ShiharaiYoteihyo.jasper";

                List<Map<String, Object>> list = new ArrayList<>();
                for (int i = 1; i <= 30; i++) {
                    HashMap<String, Object> rmap = new HashMap<>();

                    rmap.put("kaisu", i);
                    rmap.put("oshiharainengetsu", "2024 / 08 / 01");
                    rmap.put("oshiharaikingaku", nfNum.format(11000) + "　円");
                    rmap.put("oshiharaigozandaka", nfNum.format(1100000) + "　円");

                    list.add(rmap);
                }

                HashMap<String, Object> params = new HashMap<>();

                params.put("shutsuryokubi", "令和06年03月11日");
                params.put("yubimbango", "〒394-0047");
                params.put("jusho", "長野県　岡谷市　川岸中2-12-18");
                params.put("kaishamei", "遠藤ハイツ");
                params.put("namae", "福澤　翔太　様");
                params.put("keiyakubango", "10000-200118");
                params.put("keiyakubi", "2023年12月19日");
                params.put("shohimmei", "co2_中古車");
                params.put("oshiharaikaisu", "48" + "　回");
                params.put("jisshitsunenritsu", "9.500" + "　％");
                params.put("shohinkikaku", nfNum.format(1250000) + "　円");
                params.put("moshikomikin", nfNum.format(50000) + "　円");
                params.put("shohindaikinzankin", nfNum.format(1200000) + "　円");
                params.put("bunkatsushiharaitesuryo", nfNum.format(247092) + "　円");
                params.put("bunkatsushiharaikingokei", nfNum.format(14470092) + "　円");
                params.put("shokeihi", nfNum.format(11000) + "　円");
                params.put("kinyukikan", "八十八銀行");
                params.put("shiten", "岡谷支店");
                params.put("kamoku・kozabango", "普通　024****");
                params.put("kozameigi", "フクザワ　ショウタ");

                pdf = createJasper(jasperReportPath, list, params);
            }

            printJasper(pdf, model, model.getPdfName());

        } catch (

        Exception e) {
            e.printStackTrace();
            result = false;
        }
        return result;
    }

    // pdfをjasperにて作成
    public JasperPrint createJasper(String jasperReportPath, List<Map<String, Object>> pdfList, HashMap<String, Object> params) throws Exception {
        JasperPrint pdf = new JasperPrint();
        pdf = JasperFillManager.fillReport(jasperReportPath, params, new JRBeanCollectionDataSource(pdfList));

        return pdf;
    }

    // pdfを連結
    public JasperPrint combinePdf(ArrayList<JasperPrint> pdfs) {
        JasperPrint resultPdf = null;
        for (JasperPrint pdf : pdfs) {
            for (JRPrintPage page : pdf.getPages()) {
                if (resultPdf == null) {
                    resultPdf = pdf;

                } else {
                    resultPdf.addPage(page);
                }
            }
        }
        return resultPdf;
    }

    // pdfを印刷する
    public boolean printJasper(JasperPrint pdf, PdfModel model, String fileName) {
        boolean result = true;
        try {
            byte[] b = JasperExportManager.exportReportToPdf(pdf);
            model.setInputStream(new ByteArrayInputStream(b));

            Calendar c = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
            model.setFileName(URLEncoder.encode(sdf.format(c.getTime()), "UTF-8") + "_" + fileName + ".pdf");
            model.setContentLength(b.length);
        } catch (Exception e) {
            e.printStackTrace();
            result = false;
        }
        return result;

    }

}