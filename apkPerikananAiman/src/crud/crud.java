/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package crud;

import java.io.File;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTable;
import java.sql.ResultSetMetaData;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

    public class crud {
    private Connection Koneksidb;
    private String username="root";
    private String password="";
    private String dbname="perikanan"; 
    private String urlKoneksi="jdbc:mysql://localhost/"+dbname;
    
    public boolean duplikasi=false;
    public String CEK_NAMA_PRODUK, CEK_DESKRIPSI, CEK_KATEGORI, CEK_HARGA, CEK_BERAT, CEK_STOK = null;
    public String CEK_NAMA_KURIR, CEK_KODE_KURIR, CEK_ONGKOS_KIRIM = null;
    public String CEK_NAMA_TOKO, CEK_ALAMAT_TOKO, CEK_NO_TLP_TOKO, CEK_RATING, CEK_PRODUK_TOKO= null;
    public String CEK_INVOICE, CEK_TGL_BELI, CEK_NAMA_TOKO_TR, CEK_NAMA_PRODUK_TR, CEK_HARGA_TR, CEK_JLH_PRODUK, CEK_TOTAL_HARGA, CEK_KODE_KURIR_TR, CEK_ONGKOS_KIRIM_TR, CEK_JLH_TOTAL = null;

    
    public crud(){
        try {
            Driver dbdriver = new com.mysql.jdbc.Driver();
            DriverManager.registerDriver(dbdriver);
            Koneksidb=DriverManager.getConnection(urlKoneksi,username,password);
            System.out.print("Database Terkoneksi");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,e.toString());
        }
    }

   
    public void simpanProduk01(String id, String nama_produk, String deskripsi, String kategori, String harga, String berat, String stok){
        try {
            // Kolom 'gambar_produk' DIHILANGKAN
            String sqlsimpan="insert into produk(id, nama_produk, deskripsi, kategori, harga, berat, stok) value"
                    + " ('"+id+"', '"+nama_produk+"', '"+deskripsi+"', '"+kategori+"', '"+harga+"', '"+berat+"', '"+stok+"')";
            String sqlcari="select*from produk where id='"+id+"'";
            
            Statement cari=Koneksidb.createStatement();
            ResultSet data=cari.executeQuery(sqlcari);
            
            if (data.next()){
                JOptionPane.showMessageDialog(null, "ID Produk sudah terdaftar");
            } else {
                Statement perintah=Koneksidb.createStatement();
                perintah.execute(sqlsimpan);
                JOptionPane.showMessageDialog(null, "Data Produk berhasil disimpan");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    
  
    public void simpanProduk02(String id, String nama_produk, String deskripsi, String kategori, String harga, String berat, String stok){
        try {
            String sqlsimpan="INSERT INTO produk (id, nama_produk, deskripsi, kategori, harga, berat, stok) VALUES (?, ?, ?, ?, ?, ?, ?)";
            String sqlcari= "SELECT*FROM produk WHERE id = ?";
            
            PreparedStatement cari = Koneksidb.prepareStatement(sqlcari);
            cari.setString(1, id);
            ResultSet data = cari.executeQuery();
            
            if (data.next()){
                JOptionPane.showMessageDialog(null, "ID Produk sudah terdaftar");
                this.duplikasi = true;
                this.CEK_NAMA_PRODUK = data.getString("nama_produk");
                this.CEK_DESKRIPSI = data.getString("deskripsi");
                this.CEK_KATEGORI = data.getString("kategori");
                this.CEK_HARGA = data.getString("harga");
                this.CEK_BERAT = data.getString("berat");
                this.CEK_STOK = data.getString("stok");
               
            } else {
                this.duplikasi = false;
                this.CEK_NAMA_PRODUK = null;
                this.CEK_DESKRIPSI = null;
                this.CEK_KATEGORI = null;
                this.CEK_HARGA = null;
                this.CEK_BERAT = null;
                this.CEK_STOK = null;
                
                PreparedStatement perintah = Koneksidb.prepareStatement(sqlsimpan);
                perintah.setString(1, id);
                perintah.setString(2, nama_produk);
                perintah.setString(3, deskripsi);
                perintah.setString(4, kategori);
                perintah.setString(5, harga);
                perintah.setString(6, berat);
                perintah.setString(7, stok);
                perintah.executeUpdate();
                JOptionPane.showMessageDialog(null, "Data Produk berhasil disimpan");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    
    public void ubahProduk(String id, String nama_produk, String deskripsi, String kategori, String harga, String berat, String stok){
        try {
            String sqlubah="UPDATE produk SET nama_produk = ?, deskripsi = ?, kategori = ?, harga = ?, berat = ?, stok = ? WHERE id = ?";
            PreparedStatement perintah = Koneksidb.prepareStatement(sqlubah);
            perintah.setString(1, nama_produk);
            perintah.setString(2, deskripsi);
            perintah.setString(3, kategori);
            perintah.setString(4, harga);
            perintah.setString(5, berat);
            perintah.setString(6, stok);
            perintah.setString(7, id); 
            perintah.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data Produk berhasil diubah");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    
    public void hapusProduk(String id){
        try {
            String sqlhapus="DELETE FROM produk WHERE id = ?";
            PreparedStatement perintah = Koneksidb.prepareStatement(sqlhapus);
            perintah.setString(1, id);
            perintah.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data Produk berhasil dihapus");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    
    public void tampilDataProduk(JTable komponentabel, String SQL){
        try {
            PreparedStatement perintah = Koneksidb.prepareStatement(SQL);
            ResultSet data = perintah.executeQuery();
            ResultSetMetaData meta = data.getMetaData();
            int jumlahkolom = meta.getColumnCount();
            DefaultTableModel modeltabel = new DefaultTableModel();
            
            modeltabel.addColumn("ID");
            modeltabel.addColumn("Nama Produk");
            modeltabel.addColumn("Deskripsi");
            modeltabel.addColumn("Kategori");
            modeltabel.addColumn("Harga");
            modeltabel.addColumn("Berat");
            modeltabel.addColumn("Stok");
            
            while(data.next()){
                Object[] row = new Object[jumlahkolom];
                for(int i=1; i<=jumlahkolom; i++){
                    row[i-1]=data.getObject(i);
                }
                modeltabel.addRow(row);
            }
            komponentabel.setModel(modeltabel);
        } catch (Exception e) {
           
        }
    }

    
    public void simpanKurir01(String id_kurir, String nama_kurir, String kode_kurir, String ongkos_kirim){
        try {
            String sqlsimpan="insert into kurir(id_kurir, nama_kurir, kode_kurir, ongkos_kirim) value"
                    + " ('"+id_kurir+"', '"+nama_kurir+"', '"+kode_kurir+"', '"+ongkos_kirim+"')";
            String sqlcari="select*from kurir where id_kurir='"+id_kurir+"'";
            
            Statement cari=Koneksidb.createStatement();
            ResultSet data=cari.executeQuery(sqlcari);
            
            if (data.next()){
                JOptionPane.showMessageDialog(null, "ID Kurir sudah terdaftar");
            } else {
                Statement perintah=Koneksidb.createStatement();
                perintah.execute(sqlsimpan);
                JOptionPane.showMessageDialog(null, "Data Kurir berhasil disimpan");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }


    public void simpanKurir02(String id_kurir, String nama_kurir, String kode_kurir, String ongkos_kirim){
        try {
            String sqlsimpan="INSERT INTO kurir (id_kurir, nama_kurir, kode_kurir, ongkos_kirim) VALUES (?, ?, ?, ?)";
            String sqlcari= "SELECT*FROM kurir WHERE id_kurir = ?";
            
            PreparedStatement cari = Koneksidb.prepareStatement(sqlcari);
            cari.setString(1, id_kurir);
            ResultSet data = cari.executeQuery();
            
            if (data.next()){
                JOptionPane.showMessageDialog(null, "ID Kurir sudah terdaftar");
                this.duplikasi = true;
                this.CEK_NAMA_KURIR = data.getString("nama_kurir");
                this.CEK_KODE_KURIR = data.getString("kode_kurir");
                this.CEK_ONGKOS_KIRIM = data.getString("ongkos_kirim");
            } else {
                this.duplikasi = false;
                this.CEK_NAMA_KURIR = null;
                this.CEK_KODE_KURIR = null;
                this.CEK_ONGKOS_KIRIM = null;
                
                PreparedStatement perintah = Koneksidb.prepareStatement(sqlsimpan);
                perintah.setString(1, id_kurir);
                perintah.setString(2, nama_kurir);
                perintah.setString(3, kode_kurir);
                perintah.setString(4, ongkos_kirim);
                perintah.executeUpdate();
                JOptionPane.showMessageDialog(null, "Data Kurir berhasil disimpan");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    
    public void ubahKurir(String id_kurir, String nama_kurir, String kode_kurir, String ongkos_kirim){
        try {
            String sqlubah="UPDATE kurir SET nama_kurir = ?, kode_kurir = ?, ongkos_kirim = ? WHERE id_kurir = ?";
            PreparedStatement perintah = Koneksidb.prepareStatement(sqlubah);
            perintah.setString(1, nama_kurir);
            perintah.setString(2, kode_kurir);
            perintah.setString(3, ongkos_kirim);
            perintah.setString(4, id_kurir);
            perintah.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data Kurir berhasil diubah");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    
    public void hapusKurir(String id_kurir){
        try {
            String sqlhapus="DELETE FROM kurir WHERE id_kurir = ?";
            PreparedStatement perintah = Koneksidb.prepareStatement(sqlhapus);
            perintah.setString(1, id_kurir);
            perintah.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data Kurir berhasil dihapus");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    
    public void tampilDataKurir(JTable komponentabel, String SQL){
        try {
            PreparedStatement perintah = Koneksidb.prepareStatement(SQL);
            ResultSet data = perintah.executeQuery();
            ResultSetMetaData meta = data.getMetaData();
            int jumlahkolom = meta.getColumnCount();
            DefaultTableModel modeltabel = new DefaultTableModel();
            
            modeltabel.addColumn("ID Kurir");
            modeltabel.addColumn("Nama Kurir");
            modeltabel.addColumn("Kode Kurir");
            modeltabel.addColumn("Ongkos Kirim");
            
            while(data.next()){
                Object[] row = new Object[jumlahkolom];
                for(int i=1; i<=jumlahkolom; i++){
                    row[i-1]=data.getObject(i);
                }
                modeltabel.addRow(row);
            }
            komponentabel.setModel(modeltabel);
        } catch (Exception e) {
            // e.printStackTrace();
        }
    }

    
    public void simpanToko01(String id_toko, String nama_toko, String alamat_toko, String no_tlp_toko, String rating, String produk){
        try {
            String sqlsimpan="insert into toko(id_toko, nama_toko, alamat_toko, no_tlp_toko, rating, produk) value"
                    + " ('"+id_toko+"', '"+nama_toko+"', '"+alamat_toko+"', '"+no_tlp_toko+"', '"+rating+"', '"+produk+"')";
            String sqlcari="select*from toko where id_toko='"+id_toko+"'";
            
            Statement cari=Koneksidb.createStatement();
            ResultSet data=cari.executeQuery(sqlcari);
            
            if (data.next()){
                JOptionPane.showMessageDialog(null, "ID Toko sudah terdaftar");
            } else {
                Statement perintah=Koneksidb.createStatement();
                perintah.execute(sqlsimpan);
                JOptionPane.showMessageDialog(null, "Data Toko berhasil disimpan");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    
    public void simpanToko02(String id_toko, String nama_toko, String alamat_toko, String no_tlp_toko, String rating, String produk){
        try {
            String sqlsimpan="INSERT INTO toko (id_toko, nama_toko, alamat_toko, no_tlp_toko, rating, produk) VALUES (?, ?, ?, ?, ?, ?)";
            String sqlcari= "SELECT*FROM toko WHERE id_toko = ?";
            
            PreparedStatement cari = Koneksidb.prepareStatement(sqlcari);
            cari.setString(1, id_toko);
            ResultSet data = cari.executeQuery();
            
            if (data.next()){
                JOptionPane.showMessageDialog(null, "ID Toko sudah terdaftar");
                this.duplikasi = true;
                this.CEK_NAMA_TOKO = data.getString("nama_toko");
                this.CEK_ALAMAT_TOKO = data.getString("alamat_toko");
                this.CEK_NO_TLP_TOKO = data.getString("no_tlp_toko");
                this.CEK_RATING = data.getString("rating");
                this.CEK_PRODUK_TOKO = data.getString("produk");
            } else {
                this.duplikasi = false;
                this.CEK_NAMA_TOKO = null;
                this.CEK_ALAMAT_TOKO = null;
                this.CEK_NO_TLP_TOKO = null;
                this.CEK_RATING = null;
                this.CEK_PRODUK_TOKO = null;
                
                PreparedStatement perintah = Koneksidb.prepareStatement(sqlsimpan);
                perintah.setString(1, id_toko);
                perintah.setString(2, nama_toko);
                perintah.setString(3, alamat_toko);
                perintah.setString(4, no_tlp_toko);
                perintah.setString(5, rating);
                perintah.setString(6, produk);
                perintah.executeUpdate();
                JOptionPane.showMessageDialog(null, "Data Toko berhasil disimpan");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    
    public void ubahToko(String id_toko, String nama_toko, String alamat_toko, String no_tlp_toko, String rating, String produk){
        try {
            String sqlubah="UPDATE toko SET nama_toko = ?, alamat_toko = ?, no_tlp_toko = ?, rating = ?, produk = ? WHERE id_toko = ?";
            PreparedStatement perintah = Koneksidb.prepareStatement(sqlubah);
            perintah.setString(1, nama_toko);
            perintah.setString(2, alamat_toko);
            perintah.setString(3, no_tlp_toko);
            perintah.setString(4, rating);
            perintah.setString(5, produk);
            perintah.setString(6, id_toko);
            perintah.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data Toko berhasil diubah");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    
    public void hapusToko(String id_toko){
        try {
            String sqlhapus="DELETE FROM toko WHERE id_toko = ?";
            PreparedStatement perintah = Koneksidb.prepareStatement(sqlhapus);
            perintah.setString(1, id_toko);
            perintah.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data Toko berhasil dihapus");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    
    public void tampilDataToko(JTable komponentabel, String SQL){
        try {
            PreparedStatement perintah = Koneksidb.prepareStatement(SQL);
            ResultSet data = perintah.executeQuery();
            ResultSetMetaData meta = data.getMetaData();
            int jumlahkolom = meta.getColumnCount();
            DefaultTableModel modeltabel = new DefaultTableModel();
            
            modeltabel.addColumn("ID Toko");
            modeltabel.addColumn("Nama Toko");
            modeltabel.addColumn("Alamat Toko");
            modeltabel.addColumn("No Telp");
            modeltabel.addColumn("Rating");
            modeltabel.addColumn("Produk");
            
            while(data.next()){
                Object[] row = new Object[jumlahkolom];
                for(int i=1; i<=jumlahkolom; i++){
                    row[i-1]=data.getObject(i);
                }
                modeltabel.addRow(row);
            }
            komponentabel.setModel(modeltabel);
        } catch (Exception e) {
            
        }
    }
    

    public void simpanTransaksi01(String id, String invoice, String tgl_beli, String nama_toko, String nama_produk, String harga, String jlh_produk, String total_harga, String kode_kurir, String ongkos_kirim, String jlh_total){
        try {
            String sqlsimpan="insert into transaksi(id, invoice, tgl_beli, nama_toko, nama_produk, harga, jlh_produk, total_harga, kode_kurir, ongkos_kirim, jlh_total) value"
                    + " ('"+id+"', '"+invoice+"', '"+tgl_beli+"', '"+nama_toko+"', '"+nama_produk+"', '"+harga+"', '"+jlh_produk+"', '"+total_harga+"', '"+kode_kurir+"', '"+ongkos_kirim+"', '"+jlh_total+"')";
            String sqlcari="select*from transaksi where id='"+id+"'";
            
            Statement cari=Koneksidb.createStatement();
            ResultSet data=cari.executeQuery(sqlcari);
            
            if (data.next()){
                JOptionPane.showMessageDialog(null, "ID Transaksi sudah terdaftar");
            } else {
                Statement perintah=Koneksidb.createStatement();
                perintah.execute(sqlsimpan);
                JOptionPane.showMessageDialog(null, "Data Transaksi berhasil disimpan");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    

    public void simpanTransaksi02(String id, String invoice, String tgl_beli, String nama_toko, String nama_produk, String harga, String jlh_produk, String total_harga, String kode_kurir, String ongkos_kirim, String jlh_total){
        try {
            String sqlsimpan="INSERT INTO transaksi (id, invoice, tgl_beli, nama_toko, nama_produk, harga, jlh_produk, total_harga, kode_kurir, ongkos_kirim, jlh_total) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            String sqlcari= "SELECT*FROM transaksi WHERE id = ?";
            
            PreparedStatement cari = Koneksidb.prepareStatement(sqlcari);
            cari.setString(1, id);
            ResultSet data = cari.executeQuery();
            
            if (data.next()){
                JOptionPane.showMessageDialog(null, "ID Transaksi sudah terdaftar");
                this.duplikasi = true;
                this.CEK_INVOICE = data.getString("invoice");
                this.CEK_TGL_BELI = data.getString("tgl_beli");
                this.CEK_NAMA_TOKO_TR = data.getString("nama_toko");
                this.CEK_NAMA_PRODUK_TR = data.getString("nama_produk");
                this.CEK_HARGA_TR = data.getString("harga");
                this.CEK_JLH_PRODUK = data.getString("jlh_produk");
                this.CEK_TOTAL_HARGA = data.getString("total_harga");
                this.CEK_KODE_KURIR_TR = data.getString("kode_kurir");
                this.CEK_ONGKOS_KIRIM_TR = data.getString("ongkos_kirim");
                this.CEK_JLH_TOTAL = data.getString("jlh_total");
            } else {
                this.duplikasi = false;
                this.CEK_INVOICE = null;
                this.CEK_TGL_BELI = null;
                this.CEK_NAMA_TOKO_TR = null;
                this.CEK_NAMA_PRODUK_TR = null;
                this.CEK_HARGA_TR = null;
                this.CEK_JLH_PRODUK = null;
                this.CEK_TOTAL_HARGA = null;
                this.CEK_KODE_KURIR_TR = null;
                this.CEK_ONGKOS_KIRIM_TR = null;
                this.CEK_JLH_TOTAL = null;
                
                PreparedStatement perintah = Koneksidb.prepareStatement(sqlsimpan);
                perintah.setString(1, id);
                perintah.setString(2, invoice);
                perintah.setString(3, tgl_beli);
                perintah.setString(4, nama_toko);
                perintah.setString(5, nama_produk);
                perintah.setString(6, harga);
                perintah.setString(7, jlh_produk);
                perintah.setString(8, total_harga);
                perintah.setString(9, kode_kurir);
                perintah.setString(10, ongkos_kirim);
                perintah.setString(11, jlh_total);
                perintah.executeUpdate();
                JOptionPane.showMessageDialog(null, "Data Transaksi berhasil disimpan");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    
    public void ubahTransaksi(String id, String invoice, String tgl_beli, String nama_toko, String nama_produk, String harga, String jlh_produk, String total_harga, String kode_kurir, String ongkos_kirim, String jlh_total){
        try {
            String sqlubah="UPDATE transaksi SET invoice = ?, tgl_beli = ?, nama_toko = ?, nama_produk = ?, harga = ?, jlh_produk = ?, total_harga = ?, kode_kurir = ?, ongkos_kirim = ?, jlh_total = ? WHERE id = ?";
            PreparedStatement perintah = Koneksidb.prepareStatement(sqlubah);
            perintah.setString(1, invoice);
            perintah.setString(2, tgl_beli);
            perintah.setString(3, nama_toko);
            perintah.setString(4, nama_produk);
            perintah.setString(5, harga);
            perintah.setString(6, jlh_produk);
            perintah.setString(7, total_harga);
            perintah.setString(8, kode_kurir);
            perintah.setString(9, ongkos_kirim);
            perintah.setString(10, jlh_total);
            perintah.setString(11, id);
            perintah.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data Transaksi berhasil diubah");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    
    public void hapusTransaksi(String id){
        try {
            String sqlhapus="DELETE FROM transaksi WHERE id = ?";
            PreparedStatement perintah = Koneksidb.prepareStatement(sqlhapus);
            perintah.setString(1, id);
            perintah.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data Transaksi berhasil dihapus");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    
    public void tampilDataTransaksi(JTable komponentabel, String SQL){
        try {
            PreparedStatement perintah = Koneksidb.prepareStatement(SQL);
            ResultSet data = perintah.executeQuery();
            ResultSetMetaData meta = data.getMetaData();
            int jumlahkolom = meta.getColumnCount();
            DefaultTableModel modeltabel = new DefaultTableModel();
            
            modeltabel.addColumn("ID");
            modeltabel.addColumn("Invoice");
            modeltabel.addColumn("Tgl Beli");
            modeltabel.addColumn("Nama Toko");
            modeltabel.addColumn("Nama Produk");
            modeltabel.addColumn("Harga");
            modeltabel.addColumn("Jlh Produk");
            modeltabel.addColumn("Total Harga");
            modeltabel.addColumn("Kode Kurir");
            modeltabel.addColumn("Ongkos Kirim");
            modeltabel.addColumn("Jlh Total");
            
            while(data.next()){
                Object[] row = new Object[jumlahkolom];
                for(int i=1; i<=jumlahkolom; i++){
                    row[i-1]=data.getObject(i);
                }
                modeltabel.addRow(row);
            }
            komponentabel.setModel(modeltabel);
        } catch (Exception e) {
            
        }
    }
    
    public void cetaklaporan(String filelaporan, String sql){
        try {
            File file = new File(filelaporan);
            JasperDesign jasdes = JRXmlLoader.load(file);
            JRDesignQuery query = new JRDesignQuery();
            query.setText(sql);
            jasdes.setQuery(query);
            JasperReport jr = JasperCompileManager.compileReport(jasdes);
            JasperPrint jp = JasperFillManager.fillReport(jr, null, this.Koneksidb);
            JasperViewer.viewReport(jp, false);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
} 