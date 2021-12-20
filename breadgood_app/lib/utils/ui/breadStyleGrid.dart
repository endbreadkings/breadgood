import 'package:flutter/material.dart';

class BreadStylePage extends StatefulWidget {
  // BreadStylePage({Key key, this.title}) : super(key:key);
  //
  // final int breadStyleId;
  int breadStyleId;
  // final int updatedBreadStyleId;
  // const BreadStylePage(this.breadStyleId, this.updatedBreadStyleId);
  // const BreadStylePage(this.breadStyleId);
  BreadStylePage(this.breadStyleId);
  // const BreadStylePage({Key key, this.breadStyleId}):super(key:key);


  @override
  // _BreadStylePageState createState() => _BreadStylePageState(selectedIndex: this.breadStyleId);
  _BreadStylePageState createState() => _BreadStylePageState();

  int getBreadStyleId() {
    return breadStyleId;
  }
}

class _BreadStylePageState extends State<BreadStylePage> {
  List<String> _imageList = List();
  List<String> breadstyleImages = [
    'asset/images/breadstyle/breadstyle_cream.png',
    'asset/images/breadstyle/breadstyle_sweet.png',
    'asset/images/breadstyle/breadstyle_plain.png',
    'asset/images/breadstyle/breadstyle_savory.png',];

  List<String> breadstyleImagesSelected = [
    'asset/images/breadstyle/breadstyle_cream_selected.png',
    'asset/images/breadstyle/breadstyle_sweet_selected.png',
    'asset/images/breadstyle/breadstyle_plain_selected.png',
    'asset/images/breadstyle/breadstyle_savory_selected.png',];
  int selectedIndex = -1;
  // int get fetchSelectedIndex => selectedIndex;
  // List<bool> _selected = List();
  bool _selected = true;

  // _BreadStylePageState({this.selectedIndex});

  @override
  void initState() {
    // super.initState();
    print('bread style gridview init');
    selectedIndex = widget.breadStyleId - 1;
    print(selectedIndex);
    for(int i = 0; i < breadstyleImages.length; i++) {
      if(selectedIndex == i) {
        _imageList.add(breadstyleImagesSelected[i]);
      }else {
        _imageList.add(breadstyleImages[i]);
      }
    }
  }

  @override
  Widget build(BuildContext context) {
    // if(_selected) {
    //
    // }

    return Scaffold(
      // appBar: AppBar(
      //   leading: Image.asset('asset/images/breadstyle/breadstyle_cream.png'),
      // ),

      body: _createBody(),
    );
  }

  //       Image.asset('asset/images/breadstyle/breadstyle_cream.png'),
  //       Image.asset('asset/images/breadstyle/breadstyle_sweet.png'),
  //       Image.asset('asset/images/breadstyle/breadstyle_savory.png'),
  //       Image.asset('asset/images/breadstyle/breadstyle_plain.png'),



  Widget _createBody() {
    return GridView.builder(
      // crossAxisCount: 2,
      // mainAxisSpacing: 4.0,
      // crossAxisSpacing: 4.0,
      gridDelegate: SliverGridDelegateWithFixedCrossAxisCount(
        crossAxisCount: 2,
        mainAxisSpacing: 4.0,
        crossAxisSpacing: 4.0,
        childAspectRatio: 1.5,
        // mainAxisExtent: 0.0,
      ),
      primary: false,
      // itemCount: _imageList.length,
      itemCount: 4,
      itemBuilder: (BuildContext context, int index) {
        return getGridTile(index);
      },
      padding: const EdgeInsets.all(4.0),
    );
  }

  GridTile getGridTile(int index) {
    // if(_selected) {
    return GridTile(
      // header: GridTileBar(
      //
      // ),
        child: GestureDetector(
          child: Container(
            child: Image.asset(_imageList[index],
                fit: BoxFit.cover),
          ),

          onTap:() {
            setState(() {
              print('onTap');
              // same item selected -> toggle
              print('_selected Index onTap');
              print(index);
              if(selectedIndex == index) {
                selectedIndex = -1;
                _selected = !_selected;
                // _selected[index] = !_selected[index];
              }
              // other item selected -> disable previously selected item
              else {
                if(selectedIndex != -1) _imageList[selectedIndex] = breadstyleImages[selectedIndex];
                selectedIndex = index;
                widget.breadStyleId = selectedIndex;
                print('bread style update?');
                print(widget.breadStyleId);
                // _selected[_selectedIndex] = !_selected[_selectedIndex];
                // _selected[index] = !_selected[index];
              }
              _selected == true
                  ?_imageList[index] = breadstyleImagesSelected[index]
                  :_imageList[index] = breadstyleImages[index];
            });
          },));
    // }
  }
}
