
import 'package:get/get.dart';
import 'package:breadgood_app/modules/my_page/model/userinfo.dart';

class MyPageController extends GetxController {
  User user;

  UpdateUser(User updatedUser) {
    user = updatedUser;
    print('updatedUser?\n'
        'id: ${updatedUser.id}\n'
    );
    update();
  }

}