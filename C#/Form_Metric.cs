using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using DSSCOMMasterLib;

namespace WindowsFormsApplication1
{
    public partial class Form_Metric : Form
    {
        public Form_Metric()
        {
            InitializeComponent();
        }

        // メトリックの定義情報の抽出
        private void button1_Click(object sender, EventArgs e)
        {
            string metricGuid = "67407E8F4B7C8EC6791C8D9CC41B8249";

            try
            {
                MSTRConn con1 = new MSTRConn();

                IDSSSession oSession = con1.getSession();

                IDSSSource oSource = oSession.ObjectSource;

                IDSSSearch oSearch = (DSSCOMMasterLib.IDSSSearch)oSource.NewObject(EnumDSSObjectType.DssTypeSearch,EnumDSSSourceFlags.DssSourceDefault, null);
 
                IDSSObjectInfo oInfo = oSource.FindObject(metricGuid, EnumDSSObjectType.DssTypeMetric, EnumDSSSourceFlags.DssSourceDefault, null, 0, 0);
                IDSSMetric metric = (IDSSMetric)oInfo;

                // 1. Expression
                Console.WriteLine("Formula: " + metric.Expression.Text);


                // 2. Filter
                IDSSFilter filter = (IDSSFilter)metric.Conditionality.Filter;
                if (filter != null)
                {
                    Console.WriteLine("Condition: " + metric.Conditionality.Filter.Expression.Text);
                }

                // 3. Level
                string key;

                for (int i = 0; i < metric.Dimensionality.Count(); i++){

                    key = "K" + i.ToString();

                    IDSSDimty ab = metric.Dimensionality;

                       // base report level
                    if (metric.Dimensionality.Item(key).UnitType.Equals(EnumDSSDimtyUnitType.DssDimtyUnitTypeReportBaseLevel))
                    {
                        Console.WriteLine("Base report level / "
                            + metric.Dimensionality.Item(key).Filtering.ToString()
                            + " / "
                            + metric.Dimensionality.Item(key).GroupBy.ToString());


                    }
                        // attribute
                    else if (metric.Dimensionality.Item(key).UnitType.Equals(EnumDSSDimtyUnitType.DssDimtyUnitTypeAttribute))
                    {
                        Console.WriteLine(((IDSSAttribute)metric.Dimensionality.Item(key).Target).Info.Name
                            + " / "
                            + metric.Dimensionality.Item(key).Filtering.ToString()
                            + " / "
                            + metric.Dimensionality.Item(key).GroupBy.ToString());
                    }
                    else
                    {
                        // dimension, report level

                    }
                }


                // 4. Aggregation
                //Console.WriteLine(metric.Subtotals.Count());

                foreach(IDSSMetricSubtotal sub in metric.Subtotals)
                {
                    // Console.WriteLine(sub.Name);
                    switch(sub.Name)
                    {
                        case "合計":
                            Console.WriteLine("Total: " + metric.Subtotals.Item("合計").Implementation.Info.Name);
                            break;

                        case "集計":
                            Console.WriteLine("Aggregation:" + metric.Subtotals.Item("集計").Implementation.Info.Name);
                            break;

                    }

                }

                // 5. Transfomation
                oSearch.Types.Clear();
                oSearch.Types.Add(EnumDSSObjectType.DssTypeRole);
                oSearch.UsedBy.Add(oInfo);
                oSearch.Domain = EnumDSSSearchDomain.DssSearchProject;

                foreach (IDSSRole oRole in oSource.ExecuteSearch(oSearch, 0, null, null, 0, 0))
                {
                    Console.WriteLine("Transformation:" + oRole.Info.Name);
                    break;
                }

                con1.close();


            }
            catch (Exception ex)
            {
                Console.WriteLine("Error: " + ex.Message.ToString());
            }
        }
    }
}
