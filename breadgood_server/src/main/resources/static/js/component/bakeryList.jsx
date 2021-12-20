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

  React.useEffect(() => {
    getBakeryCategoryList();
  }, []);

  React.useEffect(() => {
    const params = getBakeryParams();
    getBakeryList(params);
    setSelectedLocation(filter.location);
  }, [filter]);

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

  const getBreadStyleColor = (style) => {
    let color = '#B0B0B0';
    switch (style) {
      case '담백':
        color = '#8FBCFF';
        break;
      case '크림':
        color = '#FFB39A';
        break;
      case '달콤':
        color = '#D48F62';
        break;
      case '짭짤':
        color = '#FFBC4A';
        break;
    }
    return color;
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
              className={`bl-filter filter-category ${category.id === 1 ? 'blue' : 'yellow'} ${active && 'active'}`}
              onClick={() => handleFilter('category', category.id)}
            >
              {category.id === 1 ? (
                <svg width="16" height="12" viewBox="0 0 16 12" fill="none" xmlns="http://www.w3.org/2000/svg">
                  <path
                    d="M14.3988 1.59936H12.7996V0H0V7.2C0 9.85152 2.14831 12 4.79962 12H8C10.3307 11.9866 12.3158 10.2989 12.7036 8.00064H12.7996C14.5659 8.00064 16 6.56832 16 4.8V3.20064C15.9981 2.31552 15.282 1.59936 14.3988 1.59936ZM14.3988 4.8C14.3988 5.6832 13.6827 6.39936 12.7996 6.39936V3.20064H14.3988V4.8Z"/>
                </svg>
              ) : (
                <svg width="15" height="14" viewBox="0 0 15 14" fill="none" xmlns="http://www.w3.org/2000/svg">
                  <path
                    d="M15 2.24427C15 3.1436 14.4419 3.9179 13.6368 4.27481V13.1854C13.6368 13.637 13.2512 14 12.7762 14H2.2238C1.74879 14 1.36325 13.635 1.36325 13.1854V4.27279C0.55808 3.9179 0 3.14158 0 2.24427C0 1.62725 0.26413 1.06467 0.694405 0.657353C1.11403 0.260118 1.69128 0.0120985 2.3303 0H12.6314C13.9414 0 15 1.00619 15 2.24427Z"/>
                </svg>
              )}
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
                  {bakery.nickName} <span style={{ color: getBreadStyleColor(bakery.breadStyleName)}}>Pick</span>
                </div>
              </div>
              <div className="bl-li-bakery-name">
                <div className="bk-nm-text">{bakery.title}</div>
                <div className="bk-nm-img">
                  {bakery.categoryTitle === '음료&빵' && (
                    <svg width="16" height="12" viewBox="0 0 16 12" fill="none" xmlns="http://www.w3.org/2000/svg">
                      <path
                        d="M14.3988 1.59936H12.7996V0H0V7.2C0 9.85152 2.14831 12 4.79962 12H8C10.3307 11.9866 12.3158 10.2989 12.7036 8.00064H12.7996C14.5659 8.00064 16 6.56832 16 4.8V3.20064C15.9981 2.31552 15.282 1.59936 14.3988 1.59936ZM14.3988 4.8C14.3988 5.6832 13.6827 6.39936 12.7996 6.39936V3.20064H14.3988V4.8Z"
                        fill="#4579FF"/>
                    </svg>
                  )}
                  {bakery.categoryTitle === '빵에집중' && (
                    <svg width="15" height="14" viewBox="0 0 15 14" fill="none" xmlns="http://www.w3.org/2000/svg">
                      <path d="M15 2.24427C15 3.1436 14.4419 3.9179 13.6368 4.27481V13.1854C13.6368 13.637 13.2512 14 12.7762 14H2.2238C1.74879 14 1.36325 13.635 1.36325 13.1854V4.27279C0.55808 3.9179 0 3.14158 0 2.24427C0 1.62725 0.26413 1.06467 0.694405 0.657353C1.11403 0.260118 1.69128 0.0120985 2.3303 0H12.6314C13.9414 0 15 1.00619 15 2.24427Z" fill="#FEBE52"/>
                    </svg>
                  )}
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
            className="drag-bar"
            onTouchStart={((event) => {
              startY = event.touches[0].pageY;
            })}
            onTouchEnd={(event) => {
              endY = event.changedTouches[0].pageY;
              const diffY = endY - startY;
              if (diffY > 10) close();
            }}
          />
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