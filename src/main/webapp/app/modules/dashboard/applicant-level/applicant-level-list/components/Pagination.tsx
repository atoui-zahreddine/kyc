import React from 'react';
import ReactPaginate from 'react-paginate';
import { ITEMS_PER_PAGE } from 'app/shared/util/pagination.constants';

const Pagination = ({ onPageChange, totalItems }: { onPageChange: (selectedItem: { selected: number }) => void; totalItems: number }) => {
  return (
    <ReactPaginate
      breakLabel="..."
      nextLabel="next >"
      onPageChange={onPageChange}
      pageClassName="page-item"
      pageLinkClassName="page-link"
      previousClassName="page-item"
      previousLinkClassName="page-link"
      nextClassName="page-item"
      nextLinkClassName="page-link"
      breakClassName="page-item"
      breakLinkClassName="page-link"
      containerClassName="pagination"
      activeClassName="active"
      pageCount={totalItems / ITEMS_PER_PAGE}
      previousLabel="< previous"
      renderOnZeroPageCount={null}
    />
  );
};

export default Pagination;
