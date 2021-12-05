# MyCourseProjectAndroriaHotelApp

BULGARIAN:
Androria app е приложение за хотел.

Обикновен посетител на сайта може да прави резервации, да изпраща съобщения към хотела, да вижда оферти и цени,
да оставя отзиви и да разглежда различните услуги, които хотелът предлага.

Крайната цена на резервацията се определя съгласно условията на публикуваните оферти. Ако един посетител има повече от
3 направени резервации, той става VIP и може да се възползва от отстъпката за VIP. За учебната цел,
с която е направено приложението, гостът се идентифицира по имейл.

Отзивите на гостите могат да бъдат видяни в секция Reviews - ако гост не попълни име (т.е. той е анонимен),
има шедулър, който трие автоматично анонимните отзиви.

Служители на хотела могат да правят различни промени по приложението. 
Те са два типа: admin и такива без админски права (users).
Логването им става в горния десен ъгъл на екрана.

Админът разполага с повече права, като например да трие или добавя нови юзъри, да трие или добавя оферти направени 
от него или други юзъри, да вижда кой user се е логнал през деня, да добавя снимки на сайта или да ги трие.
Разполага и с други възможности.

Обикновените служители(users) могат правят оферти, да трият оферти (създадени само от тях).
Те също имат и допълнителни правомощия.

Всички users могат да премахват резервации (bookings), които са с изтекъл срок (chekOut < днешна дата).

Апчето има инициализирани юзъри, оферти, цени и отзиви.

Пароли и юзъри:
1. Админ:
admin -> парила: 12345;
2. Юзъри (в база данни са инициализирани няколко):
tuser, tuser2, tuser3, tuser4 -> вички с парола: 12345.

ENGLISH:
Androria app is hotel site aplication.

Ordinary guest of the application can make bookings, can send messages to hotel staff. 
Also he can check offers and prices sections and take a look at all the hotel services.

The final price of the booking is determined according to the conditions of the posted offers and prices.
If a guest has more than 3 bookins so far, he becomes a VIP guest and can take advanteges for vip guests.
For educational purposes the guest are identifyed by emails.

The reviwes by guests can be seen in Reviews section. If a guest is anonimous, a scheduler is deleting the review.

Hotel staff (users and admin) can make various updates. They can login at the top right corner of the screen.

Admin may add or delete users, offers made by himself or other users, delete reviews by guests, may edit prices etc.
He also can watch which user was logged during the day.

Ordinary hotel staff (users) may add or delete offers made by themselves etc. They have restricted rights comparing with the admin. 

All users can delete expiring bookings (chekOut date is in past)

For educational purposes are initialized users, offers, prices and reviews.

Users and passwords
admin,  tuser, tuser2, tuser3, tuser4 -> all of them with password 12345.
