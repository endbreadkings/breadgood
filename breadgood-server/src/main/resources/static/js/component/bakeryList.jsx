const locationList = [
  ['서울 전체', '강동구', '강북구'],
  ['강서구', '관악구', '광진구'],
  ['구로구', '금천구', '노원구'],
  ['도봉구', '동대문구', '동작구'],
  ['마포구', '서대문구', '서초구'],
  ['성동구', '성북구', '송파구'],
  ['양천구', '영등포구', '용산구'],
  ['은평구', '종로구', '중구'],
  ['중랑구', '강남구']
];

const DefaultFilter = {
  category: [],
  location: locationList[0][0]
}

const BakeryList = () => {
  let startY, endY = 0;
  const [filter, setFilter] = React.useState({...DefaultFilter});
  const [selectedLocation, setSelectedLocation] = React.useState(locationList[0][0]);
  const [bakeries, setBakeries] = React.useState([]);
  const [categories, setCategories] = React.useState([]);
  const [isOpen, setIsOpen] = React.useState(false);
  const [isLoading, setIsLoading] = React.useState(true);

  const getBakeryCategoryList = async () => {
    try {
      const { data: categoryDatas } = await axios.get('/pages/webview-api/bakery/category');
      const categoryFilters = categoryDatas.map((curCategory) => {
        return { id: curCategory.id, active: true }
      });
      const newFilters = {
        ...DefaultFilter,
        category: categoryFilters,
      }
      setFilter(newFilters);
      setCategories(categoryDatas);
    } catch (e) {
      console.error(e);
      alert('서버에서 응답이 없습니다.');
    }
  }

  const getBakeryParams = () => {
    try {
      const params = {
        city: '서울특별시',
      }
      if (filter.location !== locationList[0][0]) {
        params.district = filter.location;
      }
      params.bakeryCategories = filter.category
        .filter((curCategory) => curCategory.active)
        .map((curCategory) => curCategory.id);
      return params;
    } catch (e) {
      console.error(e);
    }
  }

  const getBakeryList = async (params) => {
    try {
      setIsLoading(true);
      const { data } = await axios.post('/pages/webview-api/bakery/search', params);
      setBakeries(data);
    } catch (e) {
      console.error(e);
    } finally {
      setIsLoading(false);
    }
  }

  const setViewHeight = () => {
    const vh = window.innerHeight * 0.01;
    document.documentElement.style.setProperty('--vh', `${vh}px`)
  }

  React.useEffect(() => {
    setViewHeight();
    getBakeryCategoryList();
    window.addEventListener('resize', setViewHeight);
  }, () => {
    window.removeEventListener('resize', setViewHeight);
  }, []);

  React.useEffect(() => {
    const params = getBakeryParams();
    getBakeryList(params);
    setSelectedLocation(filter.location);
  }, [filter]);

  React.useEffect(() => {
    if (isOpen) document.body.style.overflow = 'hidden';
    else document.body.style.overflow = 'unset';
  }, [isOpen])

  const handleFilter = (type, value) => {
    const newFilter = { ...filter }
    if (type === 'location') {
      newFilter.location = value;
    } else if (type === 'category') {
      const selectedCategory = newFilter.category.find((curCategory) => curCategory.id === value);
      selectedCategory.active = !selectedCategory.active;
    }
    setFilter(newFilter);
  }

  const close = () => {
    setIsOpen(false);
    setSelectedLocation(filter.location);
  }

  const goToRegister = () => {
    try {
      MoveToRegister.postMessage('hihi');
    } catch (e) {};
  }

  const goToDetail = (bakery) => {
    try {
      const queryParams = {
        userId: bakery.userId,
        bakeryId: bakery.id,
      };
      MoveToDetail.postMessage(JSON.stringify(queryParams));
    } catch (e) {}
  }

  return (
    <React.Fragment>
      {isLoading && (
        <div className="loading">
          <img src="/img/loading.gif" alt="loading" />
        </div>
      )}
      <div className="bl-filter-wrapper">
        {categories.map((category) => {
          const { active } = filter.category.find((curCateogry) => curCateogry.id === category.id);
          return (
            <div
              key={category.id}
              className={`bl-filter filter-category ${active && 'active'}`}
              style={{
                backgroundColor: active ? category.color : "#FFFFFF",
                border: `1px solid ${ active ? category.color : "#E0E5F0"}`
              }}
              onClick={() => handleFilter('category', category.id)}
            >
              <img src={active ? category.titleUncoloredImgUrl : category.titleColoredImgUrl} alt="카테고리 이미지"/>
              {category.title}
            </div>
          );
        })}
        <div
          className="bl-filter filter-location gray"
          onClick={() => setIsOpen(true)}
        >
          {filter.location}
          <svg width="10" height="6" viewBox="0 0 10 6" fill="none" xmlns="http://www.w3.org/2000/svg">
            <path d="M1 1L5 5L9 1" stroke="#2D2D41" strokeLinecap="round"/>
          </svg>
        </div>
      </div>
      <div className="bl-list-wrapper">
        {bakeries.length === 0 && (
          <div className="bl-list-empty-wrapper">
            <div className="bl-list-empty">
              <img src="/img/empty-list.png" alt="empty-list" />
              <div className="bl-li-empty-desc">
                원하는 빵집이 없나요?<br/>
                지금 빵집 정보를 등록하고<br/>
                나만의 최애 빵집을 자랑해보세요!
              </div>
              <button className="bakery-reg-btn" onClick={goToRegister}>
                <span>최애 빵집 등록하러 가기</span>
                <svg width="6" height="10" viewBox="0 0 6 10" fill="none" xmlns="http://www.w3.org/2000/svg">
                  <path d="M1 9L5 5L1 1" stroke="#555555" strokeLinecap="round"/>
                </svg>
              </button>
            </div>
          </div>
        )}
        {bakeries.map((bakery) => {
          return (
            <div key={bakery.id} className="bl-list-item" onClick={() => goToDetail(bakery)}>
              <div className="bl-li-register">
                <div className="rg-profile-img">
                  <img src={bakery.profileImgUrl} alt="프로필 이미지"
                       style={{ width: "22px", height: "22px" }} />
                </div>
                <div className="rg-nickname-txt">
                  {bakery.nickName} <span style={{ color: bakery.breadStyleColor }}>Pick</span>
                </div>
              </div>
              <div className="bl-li-bakery-name">
                <div className="bk-nm-text">{bakery.title}</div>
                <div className="bk-nm-img">
                  <img src={bakery.categoryImgUrl} alt="카테고리 이미지" />
                </div>
              </div>
              <div className="bl-li-signature-and-review">
                <div className="bl-li-signature">
                  {(!bakery.signatureMenus || bakery.signatureMenus.length === 0) && (
                    <div className={`signature-item ${bakery.categoryTitle === '음료&빵' ? 'blue' : 'yellow'}`}>
                      시그니처 메뉴를 등록해주세요
                    </div>
                  )}
                  {bakery.signatureMenus.map((menu, idx) => {
                    return (
                      <div
                        key={`menu${idx}`}
                        className={`signature-item ${bakery.categoryTitle === '음료&빵' ? 'blue' : 'yellow'}`}>
                        <span className="sg-item-hashtag">#</span>{menu}
                      </div>
                    )
                  })}
                </div>
                <div className="bl-li-review">
                  {bakery.content ? (
                    <React.Fragment>
                      <svg width="13" height="14" viewBox="0 0 13 14" fill="none" xmlns="http://www.w3.org/2000/svg">
                        <path d="M8.47746 9.81527H3.66412" stroke="#C0C0C0" strokeLinecap="round" strokeLinejoin="round"/>
                        <path d="M8.47746 7.02425H3.66412" stroke="#C0C0C0" strokeLinecap="round" strokeLinejoin="round"/>
                        <path d="M5.50087 4.24007H3.6642" stroke="#C0C0C0" strokeLinecap="round" strokeLinejoin="round"/>
                        <path fillRule="evenodd" clipRule="evenodd" d="M8.60573 0.833008C8.60573 0.833008 3.48773 0.835674 3.47973 0.835674C1.63973 0.847008 0.500397 2.05767 0.500397 3.90434V10.035C0.500397 11.891 1.6484 13.1063 3.5044 13.1063C3.5044 13.1063 8.62173 13.1043 8.6304 13.1043C10.4704 13.093 11.6104 11.8817 11.6104 10.035V3.90434C11.6104 2.04834 10.4617 0.833008 8.60573 0.833008Z" stroke="#C0C0C0" strokeLinecap="round" strokeLinejoin="round"/>
                      </svg>
                      {bakery.content}
                    </React.Fragment>
                  ) : '리뷰를 등록해주세요'}
                </div>
              </div>
            </div>
          );
        })}
      </div>
      <div className={`modal-wrapper ${isOpen ? 'open' : ''}`}>
        <div className="backdrop" onClick={close} />
        <div className="location-filter-modal">
          <div
            className="drag-bar-wrapper"
            onTouchStart={((event) => {
              startY = event.touches[0].pageY;
            })}
            onTouchEnd={(event) => {
              endY = event.changedTouches[0].pageY;
              const diffY = endY - startY;
              if (diffY > 10) close();
            }}
          >
            <div className="drag-bar"/>
          </div>
          <div className="location-item-wrapper">
            {locationList.map((subLocationArray, idx1) => {
              return (
                <div key={`loc-row-${idx1}`} className="lo-item-row">
                  {subLocationArray.map((curLocation, idx2) => {
                    return (
                      <div
                        key={`location-${(idx1 + 1) * (idx2 + 1)}`}
                        className={`lo-item ${curLocation === selectedLocation ? 'selected' : ''}`}
                        onClick={() => setSelectedLocation(curLocation)}
                      >
                        {curLocation}
                      </div>
                    )
                  })}
                </div>
              )
            })}
          </div>
          <div className="filter-button-wrapper">
            <button
              disabled={filter.location === selectedLocation}
              type="button"
              className="lo-filter-btn"
              onClick={() => {
                handleFilter('location', selectedLocation);
                close();
              }}
            >
              필터 적용 하기
            </button>
          </div>
        </div>
      </div>
    </React.Fragment>
  )
};

const container = document.getElementById('bakery-list-container');
ReactDOM.render(<BakeryList />, container);