import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat, getSortState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities } from './applicant-docs.reducer';
import { IApplicantDocs } from 'app/shared/model/applicant-docs.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const ApplicantDocs = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(props.location, ITEMS_PER_PAGE, 'id'), props.location.search)
  );

  const applicantDocsList = useAppSelector(state => state.applicantDocs.entities);
  const loading = useAppSelector(state => state.applicantDocs.loading);
  const totalItems = useAppSelector(state => state.applicantDocs.totalItems);

  const getAllEntities = () => {
    dispatch(
      getEntities({
        page: paginationState.activePage - 1,
        size: paginationState.itemsPerPage,
        sort: `${paginationState.sort},${paginationState.order}`,
      })
    );
  };

  const sortEntities = () => {
    getAllEntities();
    const endURL = `?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`;
    if (props.location.search !== endURL) {
      props.history.push(`${props.location.pathname}${endURL}`);
    }
  };

  useEffect(() => {
    sortEntities();
  }, [paginationState.activePage, paginationState.order, paginationState.sort]);

  useEffect(() => {
    const params = new URLSearchParams(props.location.search);
    const page = params.get('page');
    const sort = params.get(SORT);
    if (page && sort) {
      const sortSplit = sort.split(',');
      setPaginationState({
        ...paginationState,
        activePage: +page,
        sort: sortSplit[0],
        order: sortSplit[1],
      });
    }
  }, [props.location.search]);

  const sort = p => () => {
    setPaginationState({
      ...paginationState,
      order: paginationState.order === ASC ? DESC : ASC,
      sort: p,
    });
  };

  const handlePagination = currentPage =>
    setPaginationState({
      ...paginationState,
      activePage: currentPage,
    });

  const handleSyncList = () => {
    sortEntities();
  };

  const { match } = props;

  return (
    <div>
      <h2 id="applicant-docs-heading" data-cy="ApplicantDocsHeading">
        Applicant Docs
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh List
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create new Applicant Docs
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {applicantDocsList && applicantDocsList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('docType')}>
                  Doc Type <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('firstName')}>
                  First Name <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('lastName')}>
                  Last Name <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('number')}>
                  Number <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('dateOfBirth')}>
                  Date Of Birth <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('validUntil')}>
                  Valid Until <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('imageUrl')}>
                  Image Url <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('subTypes')}>
                  Sub Types <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('imageTrust')}>
                  Image Trust <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Docs Country <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {applicantDocsList.map((applicantDocs, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${applicantDocs.id}`} color="link" size="sm">
                      {applicantDocs.id}
                    </Button>
                  </td>
                  <td>{applicantDocs.docType}</td>
                  <td>{applicantDocs.firstName}</td>
                  <td>{applicantDocs.lastName}</td>
                  <td>{applicantDocs.number}</td>
                  <td>
                    {applicantDocs.dateOfBirth ? (
                      <TextFormat type="date" value={applicantDocs.dateOfBirth} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {applicantDocs.validUntil ? (
                      <TextFormat type="date" value={applicantDocs.validUntil} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{applicantDocs.imageUrl}</td>
                  <td>{applicantDocs.subTypes}</td>
                  <td>{applicantDocs.imageTrust}</td>
                  <td>
                    {applicantDocs.docsCountry ? (
                      <Link to={`country/${applicantDocs.docsCountry.id}`}>{applicantDocs.docsCountry.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${applicantDocs.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${applicantDocs.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${applicantDocs.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                        color="danger"
                        size="sm"
                        data-cy="entityDeleteButton"
                      >
                        <FontAwesomeIcon icon="trash" /> <span className="d-none d-md-inline">Delete</span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && <div className="alert alert-warning">No Applicant Docs found</div>
        )}
      </div>
      {totalItems ? (
        <div className={applicantDocsList && applicantDocsList.length > 0 ? '' : 'd-none'}>
          <div className="justify-content-center d-flex">
            <JhiItemCount page={paginationState.activePage} total={totalItems} itemsPerPage={paginationState.itemsPerPage} />
          </div>
          <div className="justify-content-center d-flex">
            <JhiPagination
              activePage={paginationState.activePage}
              onSelect={handlePagination}
              maxButtons={5}
              itemsPerPage={paginationState.itemsPerPage}
              totalItems={totalItems}
            />
          </div>
        </div>
      ) : (
        ''
      )}
    </div>
  );
};

export default ApplicantDocs;
