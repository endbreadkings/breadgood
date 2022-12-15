import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

class DefaultAppBar extends StatelessWidget with PreferredSizeWidget {
  @override
  Widget build(BuildContext context) {
    return AppBar(
      title: Row(
        mainAxisAlignment: MainAxisAlignment.start,
        children: [
          Container(
              margin: EdgeInsets.only(right: 10),
              child: SizedBox(
                  width: 40,
                  height: 30,
                  child: Image.asset(
                    'asset/images/bread_appbar.png',
                    fit: BoxFit.fill,
                  ))),
          Container(
            child: Text('빵긋',
                style: TextStyle(
                    color: Colors.black,
                    fontFamily: 'NanumSquareRoundEB',
                    fontSize: 21,
                    fontWeight: FontWeight.w900)),
          ),
        ],
      ),
      elevation: 0.0,
      backgroundColor: Colors.white,
      centerTitle: false,
      automaticallyImplyLeading: false,
    );
  }

  @override
  Size get preferredSize => Size.fromHeight(kToolbarHeight); // ✅

}
