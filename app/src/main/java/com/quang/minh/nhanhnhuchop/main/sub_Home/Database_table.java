package com.quang.minh.nhanhnhuchop.main.sub_Home;

import android.content.Context;

import com.quang.minh.nhanhnhuchop.database.database;
import com.quang.minh.nhanhnhuchop.main.Home;

public class Database_table {
    public static database database;

    public void database(Context context){
        database = new database(context, "Note.sqlite",null,1);
        database.queryData("CREATE TABLE IF NOT EXISTS CaNhan(ViTri INT(10), Score INT(10))");
        database.queryData("CREATE TABLE IF NOT EXISTS Server(Score INT(10))");
        database.queryData("CREATE TABLE IF NOT EXISTS Account(Id VARCHAR(200) PRIMARY KEY, Name VARCHAR(200))");
    }

    public void setInsert_data(){
        if(Home.insert_data==0){
            database.queryData("CREATE TABLE IF NOT EXISTS Question(Id INT(11), question VARCHAR(200)" +
                    ", answer1 VARCHAR(200), answer2 VARCHAR(200), answer3 VARCHAR(200), answer4 VARCHAR(200)," +
                    "result INT(11), hint VARCHAR(200))");
            database.queryData("INSERT INTO Question VALUES(13, 'Sấm và sét, ta nhận biết điều nào trước?', 'Sấm', 'Sét', 'Cả 2', 'Không biết', 2, 'Vận tốc ánh sáng nhanh hơn vận tốc âm thanh')");
            database.queryData("INSERT INTO Question VALUES(14, 'Có một người leo lên núi cao, ông rớt xuống cái bịch hỏi ông có chết không?', 'Có chết', 'Không chết', 'Gãy chân', 'Bể đít', 2, 'Ông rớt cái bịch')");
            database.queryData("INSERT INTO Question VALUES(15, 'Đi thì đứng, đứng thì ngã là gì?', 'Xe đạp', 'Bàn chân', 'Em bé tập đi', 'Không biết', 1, 'Đoán xem')");
            database.queryData("INSERT INTO Question VALUES(16, 'Một thằng đổi tên thì nó tên là gì?', 'Long', 'Nam', 'Quân', 'Hải', 4, 'Thằng đổi => đồi thẳng => đồi không cong => còng không đôi => còng không hai => hài không cong => hài thẳng => thằng hải =)))')");
            database.queryData("INSERT INTO Question VALUES(17, 'Giơ tay chữ v là số mấy?', '5', '4', '3', '2', 1, 'Số la mã')");
            database.queryData("INSERT INTO Question VALUES(18, 'Có ông già lên núi ổng lấy bèo thấy một con cò gầy xơ xác, tại sao ổng về?', 'Ổng mắc ị', 'Ổng có việc bận', 'Ổng sợ con cò', 'Không có bèo', 4, 'Cò gầy => cò không béo')");
            database.queryData("INSERT INTO Question VALUES(19, 'Trên chiếc đồng hồ bằng đồng có bao nhiêu loại kim?', '2', '3', '4', '5', 3, 'Khim giờ, kim phút, kim giây, kim loại')");
            database.queryData("INSERT INTO Question VALUES(20, 'Con chó và con mèo con nào thông minh hơn?', 'Con chó', 'Con mèo', '2 con ngu như nhau', 'Không biết', 1, 'Ngu như... :))')");
            database.queryData("INSERT INTO Question VALUES(21, 'Có một bà đi chợ đi được 1 lúc bà thấy tấm bảng 130m. Hỏi bà thấy gì mà tức tốc chạy về?', 'Chó đuổi', 'Có bom', 'Đường còn ca quá', 'Có thằng nghiện', 2, 'Nhìn kỹ 130m')");
            database.queryData("INSERT INTO Question VALUES(22, 'Cầm gì càng lâu sẽ càng vững?', 'Cầm lòng', 'Cầm tiền', 'Cầm đồ', 'Cầm lái', 4, 'Đoán xem')");
            Home.insert_data = 1;
            Home.editor = Home.sharedPreferences.edit();
            Home.editor.putInt("insert_data", Home.insert_data);
            Home.editor.commit();
        }
    }
}
