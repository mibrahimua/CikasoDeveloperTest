PRAGMA user_version = 1;DROP TABLE IF EXISTS user;CREATE TABLE IF NOT EXISTS user (`Id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `Username` TEXT NOT NULL, `Password` TEXT NOT NULL);DROP TABLE IF EXISTS barang;CREATE TABLE IF NOT EXISTS barang (`Id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `Nama` TEXT NOT NULL, `Merk` TEXT NOT NULL, `Keterangan` TEXT NOT NULL);CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT);INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, 'bd08489bd9d5079ed7dfd3f835f4e223');CREATE INDEX IF NOT EXISTS 'index_barang' ON 'barang' ('Id');INSERT INTO user (Username, Password) VALUES ('demo', 'demo');INSERT INTO barang (Nama, Merk, Keterangan) VALUES ('Roti', 'Sari Roti', 'Tanggal Kadaluarsa 20 Jan 2021');INSERT INTO barang (Nama, Merk, Keterangan) VALUES ('Vitamin', 'Enervon-C', 'Obat vitamin C');INSERT INTO barang (Nama, Merk, Keterangan) VALUES ('Kopi', 'Kapal Api', 'Minuman kopi');INSERT INTO barang (Nama, Merk, Keterangan) VALUES ('Sereal', 'Choco Crunch', 'Enakk');INSERT INTO barang (Nama, Merk, Keterangan) VALUES ('Smartphone', 'Samsung', 'Hape');INSERT INTO barang (Nama, Merk, Keterangan) VALUES ('Laptop', 'Asus', 'laptop');