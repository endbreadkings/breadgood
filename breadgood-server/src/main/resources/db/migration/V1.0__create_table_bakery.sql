create table bakery
(
    bakery_id       bigint auto_increment
        primary key,
    created_at      datetime     not null,
    deleted         bit          not null,
    updated_at      datetime     not null,
    city            varchar(255) null,
    district        varchar(255) null,
    road_address    varchar(255) null,
    bakery_category bigint       null,
    description     varchar(255) null,
    mapx            double       null,
    mapy            double       null,
    title           varchar(255) null,
    user            bigint       null
);

create table bakery_category
(
    bakery_category_id      bigint auto_increment
        primary key,
    created_at              datetime     not null,
    deleted                 bit          not null,
    updated_at              datetime     not null,
    color                   varchar(255) not null,
    content                 varchar(255) not null,
    marker_img_url          varchar(255) not null,
    sort_number             int          not null,
    title                   varchar(255) not null,
    title_colored_img_url   varchar(255) not null,
    title_uncolored_img_url varchar(255) not null,
    constraint `UK-bakery_category-sort_number`
        unique (sort_number)
);

create table bakery_review
(
    bakery_review_id    bigint auto_increment
        primary key,
    created_at          datetime     not null,
    deleted             bit          not null,
    updated_at          datetime     not null,
    bread_style_img_url varchar(255) null,
    bread_style_name    varchar(255) null,
    content             varchar(255) null,
    max_length          int          not null,
    min_length          int          not null,
    emoji               bigint       null,
    img_host            varchar(255) null,
    user                bigint       null,
    bakery_id           bigint       null,
    constraint `FK-bakery_review-bakery`
        foreign key (bakery_id) references bakery (bakery_id)
);

create table bakery_review_img_urls
(
    bakery_review_bakery_review_id bigint       not null,
    img_urls                       varchar(255) null,
    constraint `FK-bakery_review_img_urls-bakery_review`
        foreign key (bakery_review_bakery_review_id) references bakery_review (bakery_review_id)
);

create table bakery_review_signature_menus
(
    bakery_review_bakery_review_id bigint       not null,
    max_length                     int          not null,
    signature_menu                 varchar(255) null,
    constraint `FK-bakery_review_signature_menus-bakery_review`
        foreign key (bakery_review_bakery_review_id) references bakery_review (bakery_review_id)
);

create table bread_style
(
    bread_style_id  bigint auto_increment
        primary key,
    created_at      datetime     not null,
    deleted         bit          not null,
    updated_at      datetime     not null,
    color           varchar(50)  not null,
    content         varchar(255) not null,
    content_img_url varchar(255) not null,
    name            varchar(255) not null,
    profile_img_url varchar(255) not null,
    sort_number     int          not null,
    constraint `UK-bread_style-name`
        unique (name)
);

create table emoji
(
    emoji_id    bigint auto_increment
        primary key,
    created_at  datetime     not null,
    deleted     bit          not null,
    updated_at  datetime     not null,
    img_url     varchar(255) null,
    name        varchar(255) not null,
    sort_number int          not null,
    constraint `UK-emoji-sort_number`
        unique (sort_number)
);

create table refresh_token
(
    refresh_token_id bigint auto_increment
        primary key,
    expiry_date      datetime     not null,
    token            varchar(255) not null,
    user             bigint       null,
    constraint `UK-refresh_token-token`
        unique (token)
);

create table terms_type
(
    terms_type_id bigint auto_increment
        primary key,
    created_at    datetime     not null,
    deleted       bit          not null,
    updated_at    datetime     not null,
    name          varchar(255) not null,
    required      bit          not null,
    sort_number   int          not null
);

create table terms
(
    terms_id       bigint auto_increment
        primary key,
    created_at     datetime not null,
    deleted        bit      not null,
    updated_at     datetime not null,
    content        text     not null,
    execution_date date     not null,
    terms_type_id  bigint   null,
    constraint `FK-terms-terms_type`
        foreign key (terms_type_id) references terms_type (terms_type_id)
);

create table user
(
    user_id     bigint auto_increment
        primary key,
    created_at  datetime     not null,
    deleted     bit          not null,
    updated_at  datetime     not null,
    bread_style bigint       null,
    email       varchar(255) null,
    nick_name   varchar(255) null,
    password    varchar(255) null,
    role        varchar(255) null,
    provider    varchar(255) null,
    provider_id varchar(255) null
);

create table user_user_terms
(
    user_user_id bigint   not null,
    terms_agree  bit      not null,
    terms_date   datetime null,
    terms_type   bigint   not null,
    constraint `FK-user_user_terms-user`
        foreign key (user_user_id) references user (user_id)
);

create table withdrawal_user
(
    withdrawal_user_id bigint auto_increment
        primary key,
    created_at         datetime not null,
    deleted            bit      not null,
    updated_at         datetime not null,
    user               bigint   null
);

