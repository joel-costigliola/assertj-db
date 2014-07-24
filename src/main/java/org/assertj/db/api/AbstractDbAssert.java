package org.assertj.db.api;

import static org.assertj.db.error.ShouldHaveColumnsSize.shouldHaveColumnsSize;
import static org.assertj.db.error.ShouldHaveRowsSize.shouldHaveRowsSize;

import java.util.List;

import org.assertj.core.api.Descriptable;
import org.assertj.core.api.WritableAssertionInfo;
import org.assertj.core.description.Description;
import org.assertj.core.internal.Failures;
import org.assertj.db.error.AssertJDBException;
import org.assertj.db.type.AbstractDbData;
import org.assertj.db.type.Column;
import org.assertj.db.type.Row;

/**
 * Assertion methods about the data in a <code>{@link Table}</code> or in a <code>{@link Request}</code>.
 * 
 * @author Régis Pouiller
 *
 * @param <E> The class of the actual value (an sub-class of {@link AbstractDbData}).
 * @param <D> The class of the original assert (an sub-class of {@link AbstractDbAssert}).
 * @param <C> The class of this assert (an sub-class of {@link AbstractColumnAssert}).
 * @param <CV> The class of this assertion on the value (an sub-class of {@link AbstractColumnValueAssert}).
 * @param <R> The class of the equivalent row assert (an sub-class of {@link AbstractRowAssert}).
 * @param <RV> The class of the equivalent row assertion on the value (an sub-class of {@link AbstractRowValueAssert}).
 */
public abstract class AbstractDbAssert<E extends AbstractDbData<E>, D extends AbstractDbAssert<E, D, C, CV, R, RV>, C extends AbstractColumnAssert<E, D, C, CV, R, RV>, CV extends AbstractColumnValueAssert<E,D,C,CV,R,RV>, R extends AbstractRowAssert<E,D,C,CV,R,RV>, RV extends AbstractRowValueAssert<E,D,C,CV,R,RV>> implements
    Descriptable<D> {

  /**
   * Info on the object to assert.
   */
  private final WritableAssertionInfo info;
  /**
   * The actual value on which the assertion is.
   */
  private final E actual;
  /**
   * Class of the assertion.
   */
  private final D myself;

  /**
   * Index of the next row to get.
   */
  private int indexNextRow;
  /**
   * Index of the next column to get.
   */
  private int indexNextColumn;

  /**
   * To notice failures in the assertion.
   */
  private static Failures failures = Failures.instance();

  // Like in AbstractAssert from assertj-core :
  // we prefer not to use Class<? extends S> selfType because it would force inherited
  // constructor to cast with a compiler warning
  // let's keep compiler warning internal (when we can) and not expose them to our end users.
  /**
   * Constructor of the database assertions.
   * 
   * @param actualValue The actual value on which the assertion is.
   * @param selfType Class of the assertion
   */
  @SuppressWarnings("unchecked")
  protected AbstractDbAssert(final E actualValue, final Class<?> selfType) {
    myself = (D) selfType.cast(this);
    actual = actualValue;
    info = new WritableAssertionInfo();
  }

  /** {@inheritDoc} */
  public D as(String description, Object... args) {
    return describedAs(description, args);
  }

  /** {@inheritDoc} */
  @Override
  public D as(Description description) {
    return describedAs(description);
  }

  /** {@inheritDoc} */
  @Override
  public D describedAs(String description, Object... args) {
    info.description(description, args);
    return myself;
  }

  /** {@inheritDoc} */
  @Override
  public D describedAs(Description description) {
    info.description(description);
    return myself;
  }

  /**
   * Verifies that the number of rows is equal to the number in parameter.
   * <p>
   * Example where the assertion verifies that the table have 2 rows :
   * </p>
   * 
   * <pre>
   * assertThat(table).hasRowsSize(2);
   * </pre>
   * 
   * @param expected The number to compare to the number of rows.
   * @return {@code this} assertion object.
   * @throws AssertionError If the number of rows is different to the number in parameter.
   */
  public D hasRowsSize(int expected) {
    List<Row> rowsList = actual.getRowsList();
    int size = rowsList.size();
    if (size != expected) {
      throw failures.failure(info, shouldHaveRowsSize(size, expected));
    }
    return myself;
  }

  /**
   * Verifies that the number of columns is equal to the number in parameter.
   * <p>
   * Example where the assertion verifies that the table have 8 columns :
   * </p>
   * 
   * <pre>
   * assertThat(table).hasColumnsSize(8);
   * </pre>
   * 
   * @param expected The number to compare to the number of columns.
   * @return {@code this} assertion object.
   * @throws AssertionError If the number of columns is different to the number in parameter.
   */
  public D hasColumnsSize(int expected) {
    List<String> columnsNameList = actual.getColumnsNameList();
    int size = columnsNameList.size();
    if (size != expected) {
      throw failures.failure(info, shouldHaveColumnsSize(size, expected));
    }
    return myself;
  }

  /**
   * Returns the next {@link Row} in the list of {@link Row}.
   * 
   * @return The next {@link Row}.
   * @throws AssertJDBException If the {@code index} is out of the bounds.
   */
  protected Row getRow() {
    return getRow(indexNextRow);
  }

  /**
   * Returns the {@link Row} at the {@code index} in parameter.
   * 
   * @param index The index corresponding to the {@link Row}.
   * @return The {@link Row}.
   * @throws AssertJDBException If the {@code index} is out of the bounds.
   */
  protected Row getRow(int index) {
    int size = actual.getRowsList().size();
    if (index < 0 || index >= size) {
      throw new AssertJDBException("Index %s out of the limits [0, %s[", index, size);
    }
    Row row = actual.getRow(index);
    indexNextRow = index + 1;
    return row;
  }

  /**
   * Returns the next {@link Column} in the list of {@link Column}.
   * 
   * @return The next {@link Column}.
   * @throws AssertJDBException If the {@code index} is out of the bounds.
   */
  protected Column getColumn() {
    return getColumn(indexNextColumn);
  }

  /**
   * Returns the {@link Column} at the {@code index} in parameter.
   * 
   * @param index The index corresponding to the {@link Column}.
   * @return The {@link Column}.
   * @throws AssertJDBException If the {@code index} is out of the bounds.
   */
  protected Column getColumn(int index) {
    List<String> columnsNameList = actual.getColumnsNameList();
    int size = columnsNameList.size();
    if (index < 0 || index >= size) {
      throw new AssertJDBException("Index %s out of the limits [0, %s[", index, size);
    }
    Column column = actual.getColumn(index);
    indexNextColumn = index + 1;
    return column;
  }

  /**
   * Returns the {@link Column} corresponding to the column name in parameter.
   * 
   * @param columnName The column name.
   * @return The {@link Column}.
   * @throws NullPointerException If the column name in parameter is null.
   * @throws AssertJDBException If there is no column with this name.
   */
  protected Column getColumn(String columnName) {
    if (columnName == null) {
      throw new NullPointerException("Column name must be not null");
    }
    List<String> columnsNameList = actual.getColumnsNameList();
    int index = columnsNameList.indexOf(columnName.toUpperCase());
    if (index == -1) {
      throw new AssertJDBException("Column <%s> does not exist", columnName);
    }
    return getColumn(index);
  }
}
