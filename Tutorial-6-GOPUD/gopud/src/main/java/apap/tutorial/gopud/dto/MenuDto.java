package apap.tutorial.gopud.dto;

import java.io.Serializable;
import java.math.BigInteger;

public class MenuDto implements Serializable {

    private Long id;

    private String nama;

    private String deskripsi;

    private BigInteger harga;

    private Integer durasiMasak;

    private Long restoran;

    public MenuDto() {}

    public MenuDto(Long id, String nama, String deskripsi, BigInteger harga, Integer durasiMasak, Long restoran) {
        this.id = id;
        this.nama = nama;
        this.deskripsi = deskripsi;
        this.harga = harga;
        this.durasiMasak = durasiMasak;
        this.restoran = restoran;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public BigInteger getHarga() {
        return harga;
    }

    public void setHarga(BigInteger harga) {
        this.harga = harga;
    }

    public Integer getDurasiMasak() {
        return durasiMasak;
    }

    public void setDurasiMasak(Integer durasiMasak) {
        this.durasiMasak = durasiMasak;
    }

    public Long getRestoran() {
        return restoran;
    }

    public void setRestoran(Long restoran) {
        this.restoran = restoran;
    }
}
