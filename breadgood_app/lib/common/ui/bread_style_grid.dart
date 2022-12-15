import 'package:flutter/material.dart';

class BreadStyleGrid extends StatefulWidget {
  int breadStyleId;

  BreadStyleGrid(this.breadStyleId);

  @override
  _BreadStyleGridState createState() => _BreadStyleGridState();

  int getBreadStyleId() {
    return breadStyleId;
  }
}

class _BreadStyleGridState extends State<BreadStyleGrid> {
  bool _selected = true;
  List<String> _imageList = [];

  int selectedIndex = -1;

  final List<String> breadstyleImages = [
    'asset/images/breadstyle/breadstyle_cream.png',
    'asset/images/breadstyle/breadstyle_sweet.png',
    'asset/images/breadstyle/breadstyle_plain.png',
    'asset/images/breadstyle/breadstyle_savory.png',
  ];

  final List<String> breadstyleImagesSelected = [
    'asset/images/breadstyle/breadstyle_cream_selected.png',
    'asset/images/breadstyle/breadstyle_sweet_selected.png',
    'asset/images/breadstyle/breadstyle_plain_selected.png',
    'asset/images/breadstyle/breadstyle_savory_selected.png',
  ];

  @override
  void initState() {
    super.initState();
    selectedIndex = widget.breadStyleId - 1;
    for (int i = 0; i < breadstyleImages.length; i++) {
      if (selectedIndex == i) {
        _imageList.add(breadstyleImagesSelected[i]);
      } else {
        _imageList.add(breadstyleImages[i]);
      }
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: _createBody(),
    );
  }

  Widget _createBody() {
    return GridView.builder(
      gridDelegate: SliverGridDelegateWithFixedCrossAxisCount(
        crossAxisCount: 2,
        mainAxisSpacing: 4.0,
        crossAxisSpacing: 4.0,
        childAspectRatio: 1.5,
      ),
      primary: false,
      itemCount: 4,
      itemBuilder: (BuildContext context, int index) {
        return getGridTile(index);
      },
      padding: const EdgeInsets.all(4.0),
    );
  }

  GridTile getGridTile(int index) {
    return GridTile(
        child: GestureDetector(
      child: Container(
        child: Image.asset(_imageList[index], fit: BoxFit.cover),
      ),
      onTap: () {
        setState(() {
          if (selectedIndex == index) {
            selectedIndex = -1;
            _selected = !_selected;
          } else {
            if (selectedIndex != -1)
              _imageList[selectedIndex] = breadstyleImages[selectedIndex];
            selectedIndex = index;
            widget.breadStyleId = selectedIndex;
          }
          _selected
              ? _imageList[index] = breadstyleImagesSelected[index]
              : _imageList[index] = breadstyleImages[index];
        });
      },
    ));
    // }
  }
}
