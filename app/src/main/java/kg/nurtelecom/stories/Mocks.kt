package kg.nurtelecom.stories

import kg.nurtelecom.stories.models.ButtonModel
import kg.nurtelecom.stories.models.DetailedStory
import kg.nurtelecom.stories.models.Story

object Mocks {

    private val uris = listOf(
        DetailedStory(
            1,
            1,
            false,
            "https://flomaster.top/uploads/posts/2023-10/1697556295_flomaster-top-p-priroda-illyustratsiya-instagram-1.jpg",
            "\uD83D\uDD25 Для абонентов действует выгодное предложение!",
            "Мы рекомендуем отталкиваться от своих потребностей. Следуйте своим предпочтениям в карусели\uD83D\uDC46\n" +
                    "\n" +
                    "Не забывайте и о наших специальных тарифах для:\n" +
                    "✅ обучения,\n" +
                    "✅ семьи,\n" +
                    "✅ умных устройств,\n" +
                    "✅ роутеров и модемов,\n" +
                    "✅ регионов.\n" +
                    "\n" +
                    "Подключите на 4 недели всего за 495 сомов вместо 750 сомов! \uD83D\uDCA5\n" +
                    "\uD83D\uDCA1Самое приятное – продлевать действие этого тарифа по сниженной стоимости можно ещё 2 раза.",
            ButtonModel("Детали", "Переход по диплинку со сториса 1", "#ffffff", "#f0047f")
        ),
        DetailedStory(
            2,
            3,
            false,
            "https://img.gazeta.ru/files3/225/15619225/602fd8501fa16_img-pic_32ratio_1200x800-1200x800-70042.jpg",
            null,
            null,
            ButtonModel("Детали", "Переход по диплинку со сториса 3", "#ffffff", "#f0047f")
        ),
        DetailedStory(
            3,
            2,
            false,
            "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcRCv_Gx6Fde6mja_lLmll0fzrxRvcKLHGrPxnqMrQLWKqXi9IYy&usqp=CAU",
            "just a title so you know...",
            null,
            ButtonModel("Детали", "Переход по диплинку со сториса 2", "#ffffff", "#82B752")
        )
    )

    val stories = listOf(
        Story(
            1,
            1,
            "1-Получи 10 ГБ",
            true,
            "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcRCv_Gx6Fde6mja_lLmll0fzrxRvcKLHGrPxnqMrQLWKqXi9IYy&usqp=CAU",
            uris,
            listOf("#FF0051")
        ),
        Story(
            2,
            2,
            "2-Переходи на О!",
            false,
            "https://picsum.photos/200/300",
            uris,
            listOf("#FF0051", "#1C67F8")
        ),
        Story(
            3,
            3,
            "3-Билеты на Open Air",
            false,
            "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS2ecYTDMQHg-Q7GrDtvXE7VKQNASRMChOvQR1EZyFMKHQAIwCKwqyB8HTvFbENV0e2tBk&usqp=CAU",
            uris
        ),
        Story(
            4,
            4,
            "4-Билеты на Open Air",
            false,
            "https://flomaster.top/uploads/posts/2023-10/1697556295_flomaster-top-p-priroda-illyustratsiya-instagram-1.jpg",
            uris,
            listOf("#FF0051", "#1C67F8")
        ),
        Story(
            5,
            5,
            "5-Билеты на Open Air",
            false,
            "https://www.novochag.ru/upload/img_cache/276/276da953d07c68f7614b96b9a77be396_ce_3750x2500x0x0_cropped_666x444.jpg",
            uris
        ),
        Story(
            6,
            6,
            "6-Билеты на Open Air",
            false,
            "https://s0.rbk.ru/v6_top_pics/media/img/3/52/756723200429523.jpg",
            uris
        ),
        Story(
            7,
            7,
            "7-Билеты на Open Air",
            false,
            "https://img.gazeta.ru/files3/225/15619225/602fd8501fa16_img-pic_32ratio_1200x800-1200x800-70042.jpg",
            uris
        )
    )
}