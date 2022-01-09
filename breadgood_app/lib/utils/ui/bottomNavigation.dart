import 'package:breadgood_app/modules/login/screens/login_page.dart';
import 'package:breadgood_app/modules/main/screens/bread_store_deatil_webview.dart';
import 'package:breadgood_app/modules/main/screens/main_map.dart';
import 'package:breadgood_app/modules/my_page/screens/my_page.dart';
import 'package:breadgood_app/modules/register_bakery/screens/search_bakery.dart';
import 'package:flutter/material.dart';
import 'package:get/get.dart';

class BottomNavigation extends StatefulWidget {
  // const BottomNavigation({Key key}) : super(key: key);

  @override
  _BottomNavigationState createState() => _BottomNavigationState();
}

class _BottomNavigationState extends State<BottomNavigation> {
  int _selectedIndex = 0;

  //움직일 페이지 설정
  // final List<Widget> _children = [BaseMapPage(), SearchBakeryPage(), MyInfoEditPage(), LoginPage()];
  final _children= ["/main","/register_bakery/search_bakery_page","/my_page/my_info_edit","/further_info/further_info"];

  void _onTap(int index) {
    setState(() {
      _selectedIndex = index;
      Get.offAndToNamed(_children[index]);
    });
  }

  @override
  Widget build(BuildContext context) {
    return SizedBox(
        height: 105,
        child: BottomNavigationBar(
          type: BottomNavigationBarType.fixed,
          backgroundColor: Colors.white,
          selectedFontSize: 14,
          unselectedFontSize: 14,
          currentIndex: _selectedIndex,
          //현재 선택된 Index
          onTap: _onTap,
          selectedIconTheme:
              IconThemeData(color: Color.fromRGBO(69, 121, 255, 1), size: 14),
          unselectedLabelStyle:
              TextStyle(color: Color.fromRGBO(136, 136, 136, 1)),
          selectedLabelStyle:
              TextStyle(color: Color.fromRGBO(69, 121, 255, 1), fontSize: 14),
          items: [
            BottomNavigationBarItem(
              title: Text('홈'),
              icon: SizedBox(
                  width: 30,
                  height: 30,
                  child: Image.asset(
                      'asset/images/icon/bottomNavigation/main.png')),
            ),
            // BottomNavigationBarItem(
            //   title: Text('관심빵집'),
            //   icon: SizedBox(
            //       width: 30,
            //       height: 30,
            //       child: Image.asset(
            //           'asset/images/icon/bottomNavigation/interested_bakery.png')),
            // ),
            BottomNavigationBarItem(
              title: Text('빵집등록'),
              icon: SizedBox(
                  width: 30,
                  height: 30,
                  child: Image.asset(
                      'asset/images/icon/bottomNavigation/favorite_register.png')),
            ),
            BottomNavigationBarItem(
              title: Text('my빵긋'),
              icon: SizedBox(
                  width: 30,
                  height: 30,
                  child:
                      Image.asset('asset/images/icon/bottomNavigation/myInfo.png')),
            ),
            BottomNavigationBarItem(
              title: Text('더보기'),
              icon: SizedBox(
                  width: 30,
                  height: 30,
                  child: Image.asset(
                      'asset/images/icon/bottomNavigation/more.png')),
            ),
          ],
        ));
  }
}
