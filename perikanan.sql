-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Waktu pembuatan: 12 Jan 2026 pada 21.53
-- Versi server: 10.4.32-MariaDB
-- Versi PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `perikanan`
--

-- --------------------------------------------------------

--
-- Struktur dari tabel `kurir`
--

CREATE TABLE `kurir` (
  `id_kurir` varchar(255) NOT NULL,
  `nama_kurir` varchar(255) NOT NULL,
  `kode_kurir` varchar(255) NOT NULL,
  `ongkos_kirim` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `kurir`
--

INSERT INTO `kurir` (`id_kurir`, `nama_kurir`, `kode_kurir`, `ongkos_kirim`) VALUES
('002', 'Aiman', '123', '15.000'),
('009', 'Nabila', '321', '20.000');

-- --------------------------------------------------------

--
-- Struktur dari tabel `produk`
--

CREATE TABLE `produk` (
  `id` varchar(255) NOT NULL,
  `nama_produk` varchar(255) NOT NULL,
  `deskripsi` varchar(255) NOT NULL,
  `kategori` varchar(255) NOT NULL,
  `harga` varchar(255) NOT NULL,
  `berat` varchar(255) NOT NULL,
  `stok` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `produk`
--

INSERT INTO `produk` (`id`, `nama_produk`, `deskripsi`, `kategori`, `harga`, `berat`, `stok`) VALUES
('P001', 'Ikan Nila', 'Hasil budidaya air tawar segar', 'Ikan Segar', '35.000', '1Kg', '25'),
('P002', 'Udang', 'Berkualitas eskpor', 'Ikan Segar', '110.000', '1Kg', '15');

-- --------------------------------------------------------

--
-- Struktur dari tabel `toko`
--

CREATE TABLE `toko` (
  `id_toko` varchar(255) NOT NULL,
  `nama_toko` varchar(255) NOT NULL,
  `alamat_toko` varchar(255) NOT NULL,
  `no_tlp_toko` varchar(255) NOT NULL,
  `rating` varchar(255) NOT NULL,
  `produk` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `toko`
--

INSERT INTO `toko` (`id_toko`, `nama_toko`, `alamat_toko`, `no_tlp_toko`, `rating`, `produk`) VALUES
('T001', 'Aiman Seafood', 'Amuntai', '08561531636', '9', 'Udang segar'),
('T002', 'Samudra jaya', 'Banjar', '08561531631', '8', 'Ikan Nila');

-- --------------------------------------------------------

--
-- Struktur dari tabel `transaksi`
--

CREATE TABLE `transaksi` (
  `id` varchar(255) NOT NULL,
  `invoice` varchar(255) NOT NULL,
  `tgl_beli` varchar(255) NOT NULL,
  `nama_toko` varchar(255) NOT NULL,
  `nama_produk` varchar(255) NOT NULL,
  `harga` varchar(255) NOT NULL,
  `jlh_produk` varchar(255) NOT NULL,
  `total_harga` varchar(255) NOT NULL,
  `kode_kurir` varchar(255) NOT NULL,
  `ongkos_kirim` varchar(255) NOT NULL,
  `jlh_total` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `transaksi`
--

INSERT INTO `transaksi` (`id`, `invoice`, `tgl_beli`, `nama_toko`, `nama_produk`, `harga`, `jlh_produk`, `total_harga`, `kode_kurir`, `ongkos_kirim`, `jlh_total`) VALUES
('0002', 'INV0002', '12 Nov 2025', 'Aiman Seafood', 'Udang', '100.000', '5', '500.000', '123', '15.000', '515.000'),
('0009', 'INV0009', '25 Nov 2025', 'Samudra Jaya', 'Ikan Nila', '35.000', '2', '70.000', '321', '20.000', '90.000');

--
-- Indexes for dumped tables
--

--
-- Indeks untuk tabel `kurir`
--
ALTER TABLE `kurir`
  ADD PRIMARY KEY (`id_kurir`);

--
-- Indeks untuk tabel `produk`
--
ALTER TABLE `produk`
  ADD PRIMARY KEY (`id`);

--
-- Indeks untuk tabel `toko`
--
ALTER TABLE `toko`
  ADD PRIMARY KEY (`id_toko`);

--
-- Indeks untuk tabel `transaksi`
--
ALTER TABLE `transaksi`
  ADD PRIMARY KEY (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
