import 'package:flutter/material.dart';

class BreadStyleGridView extends StatefulWidget {
  int breadStyleId;
  BreadStyleGridView(this.breadStyleId);

  @override
  _BreadStyleGridViewState createState() => _BreadStyleGridViewState();

  int getBreadStyleId() {
    return breadStyleId;
  }
}

class _BreadStyleGridViewState extends State<BreadStyleGridView> {
  List<String> _images = [];
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
  int selectedIndex = -1;
  bool _isSelected = true;

  @override
  void initState() {
    // super.initState();
    selectedIndex = widget.breadStyleId - 1;
    for (int i = 0; i < breadstyleImages.length; i++) {
      if (selectedIndex == i) {
        _images.add(breadstyleImagesSelected[i]);
      } else {
        _images.add(breadstyleImages[i]);
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
        child: Image.asset(_images[index], fit: BoxFit.cover),
      ),
      onTap: () {
        setState(() {
          if (selectedIndex == index) {
            selectedIndex = -1;
            _isSelected = !_isSelected;
          } else {
            if (selectedIndex != -1)
              _images[selectedIndex] = breadstyleImages[selectedIndex];
            selectedIndex = index;
            widget.breadStyleId = selectedIndex;
          }
          _isSelected == true
              ? _images[index] = breadstyleImagesSelected[index]
              : _images[index] = breadstyleImages[index];
        });
      },
    ));
    // }
  }
}
